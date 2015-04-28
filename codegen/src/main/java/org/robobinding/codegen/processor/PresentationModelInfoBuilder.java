package org.robobinding.codegen.processor;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.robobinding.annotation.DependsOnStateOf;
import org.robobinding.annotation.GroupedItemPresentationModel;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.codegen.*;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class PresentationModelInfoBuilder {
	private static final String ITEM_PRESENTATION_MODEL_OBJECT_SUFFIX = "$$IPM";
	private static final String GROUPED_ITEM_PRESENTATION_MODEL_OBJECT_SUFFIX = "$$GIPM";
	
	private final TypeElementWrapper typeElement;
	private final boolean dataSetPropertyEnabled;
	private final boolean groupedDataSetPropertyEnabled;
	private final String presentationModelObjectTypeName;
	private final Set<String> filteredMethodNames = Sets.newHashSet("getPresentationModelChangeSupport");
	
	private final Set<String> propertyNames;
	private final Map<String, PropertyInfoBuilder> propertyBuilders;
	private final Map<String, DataSetPropertyInfoImpl> dataSetProperties;
	private final Map<String, GroupedDataSetPropertyInfoImpl> groupedDataSetProperties;
	private final Set<PropertyDependencyInfo> propertyDependencies;
	private final Map<String, MethodElementWrapper> eventMethods;
	
	private final PresentationModelErrors errors;


	public PresentationModelInfoBuilder(TypeElementWrapper typeElement, 
			String presentationModelObjectTypeName, boolean dataSetPropertyEnabled,
										boolean groupedDataSetPropertyEnabled) {
		this.typeElement = typeElement;
		this.dataSetPropertyEnabled = dataSetPropertyEnabled;
		this.groupedDataSetPropertyEnabled = groupedDataSetPropertyEnabled;
		this.presentationModelObjectTypeName = presentationModelObjectTypeName;
		
		propertyNames = Sets.newHashSet();
		propertyBuilders = Maps.newHashMap();
		dataSetProperties = Maps.newHashMap();
		groupedDataSetProperties = Maps.newHashMap();
		propertyDependencies = Sets.newHashSet();
		eventMethods = Maps.newHashMap();
		
		errors = new PresentationModelErrors(typeElement.typeName());
	}

	public PresentationModelInfo build() {
		analyzeFrom(typeElement);
		
		processDataSetProperties();
		processGroupedDataSetProperties();
		validateProperties();
		validateDependencies();
		
		errors.assertNoErrors();
		return createPresentationModelInfo();
	}

	private void analyzeFrom(TypeElementWrapper type) {
		if (type.isObjectType()) {
			return;
		}

		List<MethodElementWrapper> methods = type.getMethods();
		classifyMethods(methods);

		analyzeFrom(type.getSuperclass());
	}

	private void classifyMethods(List<MethodElementWrapper> methods) {
		for(MethodElementWrapper method : methods) {
			if(isFilteredMethod(method) || method.isStaticOrNonPublic() || method.hasMoreThanOneParameters()) {
				continue;
			}

			if(PropertyUtils.isGetter(method)) {
				addGetter(method);
			} else if(PropertyUtils.isSetter(method)) {
				addSetter(method);
			} else if(isEventMethod(method)){//the rest are event methods.
				addEventMethod(method);
			}
		}
		
	}

	private boolean isFilteredMethod(MethodElementWrapper method) {
		return filteredMethodNames.contains(method.methodName());
	}

	private void addGetter(MethodElementWrapper getter) {
		String propertyName = PropertyUtils.propertyNameFromGetter(getter);
		if(isDataSetProperty(getter)) {
			ItemPresentationModelAnnotationMirror annotation = new ItemPresentationModelAnnotationMirror(
					getter.getAnnotation(ItemPresentationModel.class));
			DataSetPropertyInfoImpl dataSetProperty = new DataSetPropertyInfoImpl(propertyName, getter, 
					annotation, 
					itemPresentationModelObjectTypeNameOf(annotation));
			dataSetProperties.put(propertyName, dataSetProperty);
		} else if(isGroupedDataSetProperty(getter)) {
			GroupedItemPresentationModelAnnotationMirror annotation = new GroupedItemPresentationModelAnnotationMirror(
					getter.getAnnotation(GroupedItemPresentationModel.class));
			GroupedDataSetPropertyInfoImpl groupedDataSetProperty = new GroupedDataSetPropertyInfoImpl(propertyName, getter,
					annotation, groupedItemPresentationModelObjectTypeNameOf(annotation));
			groupedDataSetProperties.put(propertyName, groupedDataSetProperty);
		} else {
			PropertyInfoBuilder propertyBuilder = getFromPropertyBuilders(propertyName);
			propertyBuilder.setGetter(getter);
		}
		
		if(getter.hasAnnotation(DependsOnStateOf.class)) {
			addDependency(propertyName, getter);
		}

		propertyNames.add(propertyName);
	}

	private boolean isDataSetProperty(MethodElementWrapper getter) {
		return dataSetPropertyEnabled && getter.hasAnnotation(ItemPresentationModel.class);
	}

	private boolean isGroupedDataSetProperty(MethodElementWrapper getter) {
		return groupedDataSetPropertyEnabled && getter.hasAnnotation(GroupedItemPresentationModel.class);
	}

	private String itemPresentationModelObjectTypeNameOf(ItemPresentationModelAnnotationMirror annotation) {
		return annotation.itemPresentationModelTypeName() + ITEM_PRESENTATION_MODEL_OBJECT_SUFFIX;
	}

	private String groupedItemPresentationModelObjectTypeNameOf(
			GroupedItemPresentationModelAnnotationMirror annotation) {
		return annotation.groupedItemPresentationModelTypeName() + GROUPED_ITEM_PRESENTATION_MODEL_OBJECT_SUFFIX;
	}

	private PropertyInfoBuilder getFromPropertyBuilders(String propertyName) {
		if(!propertyBuilders.containsKey(propertyName)) {
			PropertyInfoBuilder propertyBuilder = new PropertyInfoBuilder(propertyName);
			propertyBuilders.put(propertyName, propertyBuilder);
		}
		
		return propertyBuilders.get(propertyName);
	}
	
	private void addDependency(String propertyName, MethodElementWrapper getter) {
		DependsOnStateOfAnnotationMirror annotation = new DependsOnStateOfAnnotationMirror(
				getter.getAnnotation(DependsOnStateOf.class));
		if(annotation.hasDependentProperty()) {
			PropertyDependencyInfo dependencyInfo = new PropertyDependencyInfo(propertyName, annotation.dependentProperties());
			propertyDependencies.add(dependencyInfo);
		}
	}
	
	private void addSetter(MethodElementWrapper setter) {
		String propertyName = PropertyUtils.propertyNameFromSetter(setter);
		PropertyInfoBuilder propertyBuilder = getFromPropertyBuilders(propertyName);
		propertyBuilder.setSetter(setter);
		
		propertyNames.add(propertyName);
	}

	private boolean isEventMethod(MethodElementWrapper method) {
		return method.hasParameter() ? method.isFirstParameterNotPrimitiveType():true;
	}
	
	private void addEventMethod(MethodElementWrapper method) {
		eventMethods.put(method.methodName(), method);
	}

	private void processDataSetProperties() {
		for(DataSetPropertyInfoImpl dataSetProperty : dataSetProperties.values()) {
			try {
				validateDataSetProperty(dataSetProperty);
			} catch(RuntimeException e) {
				errors.addPropertyError(e.getMessage());
				continue;
			}
			removeSetterOfDataSetProperty(dataSetProperty);
			removeItemPresentationModelFactoryMethod(dataSetProperty);
		}
	}

	private void processGroupedDataSetProperties() {
		for(GroupedDataSetPropertyInfoImpl groupedDataSetProperty : groupedDataSetProperties.values()) {
			try {
				validateGroupedDataSetProperty(groupedDataSetProperty);
			} catch(RuntimeException e) {
				errors.addPropertyError(e.getMessage());
				continue;
			}
			removeSetterOfGroupedDataSetProperty(groupedDataSetProperty);
			removeGroupedItemPresentationModelFactoryMethod(groupedDataSetProperty);
		}
	}

	private void validateDataSetProperty(DataSetPropertyInfoImpl dataSetProperty) {
		if(dataSetProperty.isCreatedByFactoryMethod()) {
			String factoryMethod = dataSetProperty.factoryMethod();
			MethodElementWrapper eventMethod = eventMethods.get(factoryMethod);
			if((eventMethod == null) || eventMethod.hasParameter() 
					|| eventMethod.isReturnTypeNotAssignableTo(dataSetProperty.itemPresentationModelTypeName())) {
				throw new RuntimeException(MessageFormat.format("The dataSet property ''{0}'' expects an non-existing factory method ''{1}''",
						dataSetProperty.name(),
						new MethodDescription(factoryMethod, dataSetProperty.itemPresentationModelTypeName(), new Class<?>[0])));
			}
		}
	}

	private void validateGroupedDataSetProperty(GroupedDataSetPropertyInfoImpl groupedDataSetProperty) {
		if(groupedDataSetProperty.isCreatedByFactoryMethod()) {
			String factoryMethod = groupedDataSetProperty.factoryMethod();
			MethodElementWrapper eventMethod = eventMethods.get(factoryMethod);
			if((eventMethod == null) || eventMethod.hasParameter()
					|| eventMethod.isReturnTypeNotAssignableTo(groupedDataSetProperty.groupedItemPresentationModelTypeName())) {
				throw new RuntimeException(MessageFormat.format("The grouped dataSet property ''{0}'' expects an non-existing factory method ''{1}''",
						groupedDataSetProperty.name(),
						new MethodDescription(factoryMethod, groupedDataSetProperty.groupedItemPresentationModelTypeName(), new Class<?>[0])));
			}
		}
	}
	
	private void removeSetterOfDataSetProperty(DataSetPropertyInfoImpl dataSetProperty) {
		propertyBuilders.remove(dataSetProperty.name());
	}

	private void removeSetterOfGroupedDataSetProperty(GroupedDataSetPropertyInfoImpl dataSetProperty) {
		propertyBuilders.remove(dataSetProperty.name());
	}
	
	private void removeItemPresentationModelFactoryMethod(DataSetPropertyInfoImpl dataSetProperty) {
		eventMethods.remove(dataSetProperty.factoryMethod());
	}

	private void removeGroupedItemPresentationModelFactoryMethod(GroupedDataSetPropertyInfoImpl dataSetProperty) {
		eventMethods.remove(dataSetProperty.factoryMethod());
	}

	private void validateProperties() {
		for(PropertyInfoBuilder builder : propertyBuilders.values()) {
			if(builder.isGetterSetterTypeInconsistent()) {
				errors.addPropertyError(MessageFormat.format("The property ''{0}'' has an inconsistent type in getter and setter",
						builder.getPropertyName()));
			}
		}
	}

	private void validateDependencies() {
		DependencyValidation validation = new DependencyValidation(propertyNames);
		for(PropertyDependencyInfo propertyDependency : propertyDependencies) {
			try {
				validation.validate(propertyDependency.property(), propertyDependency.dependentProperties());
			} catch(RuntimeException e) {
				errors.addPropertyDependencyError(e.getMessage());
			}
		}
	}
	
	private PresentationModelInfo createPresentationModelInfo() {
		return new PresentationModelInfo(typeElement.typeName(), presentationModelObjectTypeName, 
				typeElement.isAssignableTo(HasPresentationModelChangeSupport.class),
				buildProperties(), buildDataSetProperties(), buildGroupedDataSetProperties(),
				propertyDependencies, buildEventMethods());
	}

	private Set<PropertyInfo> buildProperties() {
		Set<PropertyInfo> properties = Sets.newHashSet();
		for(PropertyInfoBuilder builder : propertyBuilders.values()) {
			PropertyInfoImpl property = builder.build();
			properties.add(property);
		}
		return properties;
	}
	
	private Set<DataSetPropertyInfo> buildDataSetProperties() {
		Set<DataSetPropertyInfo> result = Sets.newHashSet();
		for(DataSetPropertyInfoImpl dataSetProperty : dataSetProperties.values()) {
			result.add(dataSetProperty);
		}
		return result;
	}

	private Set<GroupedDataSetPropertyInfo> buildGroupedDataSetProperties() {
		Set<GroupedDataSetPropertyInfo> result = Sets.newHashSet();
		for(GroupedDataSetPropertyInfoImpl groupedDataSetProperty: groupedDataSetProperties.values()){
			result.add(groupedDataSetProperty);
		}
		return result;
	}
	
	private Set<EventMethodInfo> buildEventMethods() {
		Set<EventMethodInfo> result = Sets.newHashSet();
		for(MethodElementWrapper method : eventMethods.values()) {
			result.add(new EventMethodInfoImpl(method));
		}
		return result;
	}
}
