package org.robobinding.codegen.processor;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.robobinding.annotation.DependsOnStateOf;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.codegen.DataSetPropertyInfo;
import org.robobinding.codegen.EventMethodInfo;
import org.robobinding.codegen.PresentationModelInfo;
import org.robobinding.codegen.PropertyDependencyInfo;
import org.robobinding.codegen.PropertyInfo;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class PresentationModelInfoBuilder {
	private static final String ITEM_PRESENTATION_MODEL_OBJECT_SUFFIX = "$$IPM";
	
	private final TypeElementWrapper typeElement;
	private final boolean dataSetPropertyEnabled;
	private final String presentationModelObjectTypeName;
	
	private final Set<String> filteredMethodNames = Sets.newHashSet("getPresentationModelChangeSupport");
	
	private final Set<String> propertyNames;
	private final Map<String, PropertyInfoBuilder> propertyBuilders;
	private final Map<String, DataSetPropertyInfoImpl> dataSetProperties;
	private final Set<PropertyDependencyInfo> propertyDependencies;
	private final Map<String, MethodElementWrapper> eventMethods;
	
	private final PresentationModelErrors errors;

	
	public PresentationModelInfoBuilder(TypeElementWrapper typeElement, 
			String presentationModelObjectTypeName, boolean dataSetPropertyEnabled) {
		this.typeElement = typeElement;
		this.dataSetPropertyEnabled = dataSetPropertyEnabled;
		this.presentationModelObjectTypeName = presentationModelObjectTypeName;
		
		propertyNames = Sets.newHashSet();
		propertyBuilders = Maps.newHashMap();
		dataSetProperties = Maps.newHashMap();
		propertyDependencies = Sets.newHashSet();
		eventMethods = Maps.newHashMap();
		
		errors = new PresentationModelErrors(typeElement.typeName());
	}

	public PresentationModelInfo build() {
		analyzeFrom(typeElement);
		
		processDataSetProperties();
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

	private String itemPresentationModelObjectTypeNameOf(ItemPresentationModelAnnotationMirror annotation) {
		return annotation.itemPresentationModelTypeName() + ITEM_PRESENTATION_MODEL_OBJECT_SUFFIX;
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
	
	private void removeSetterOfDataSetProperty(DataSetPropertyInfoImpl dataSetProperty) {
		propertyBuilders.remove(dataSetProperty.name());
	}
	
	private void removeItemPresentationModelFactoryMethod(DataSetPropertyInfoImpl dataSetProperty) {
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
				buildProperties(), buildDataSetProperties(), 
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
	
	private Set<EventMethodInfo> buildEventMethods() {
		Set<EventMethodInfo> result = Sets.newHashSet();
		for(MethodElementWrapper method : eventMethods.values()) {
			result.add(new EventMethodInfoImpl(method));
		}
		return result;
	}
}
