package org.robobinding.codegen.presentationmodel.processor;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.robobinding.annotation.DependsOnStateOf;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.codegen.apt.element.GetterElement;
import org.robobinding.codegen.apt.element.MethodElement;
import org.robobinding.codegen.apt.element.SetterElement;
import org.robobinding.codegen.apt.element.WrappedTypeElement;
import org.robobinding.codegen.apt.type.WrappedDeclaredType;
import org.robobinding.codegen.apt.type.WrappedTypeMirror;
import org.robobinding.codegen.presentationmodel.DataSetPropertyInfo;
import org.robobinding.codegen.presentationmodel.EventMethodInfo;
import org.robobinding.codegen.presentationmodel.PresentationModelInfo;
import org.robobinding.codegen.presentationmodel.PropertyDependencyInfo;
import org.robobinding.codegen.presentationmodel.PropertyInfo;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class PresentationModelInfoBuilder {
	private static final String ITEM_PRESENTATION_MODEL_OBJECT_SUFFIX = "$$IPM";
	
	private final WrappedTypeElement typeElement;
	private final boolean dataSetPropertyEnabled;
	private final String presentationModelObjectTypeName;
	
	private final Set<String> propertyNames;
	private final Map<String, PropertyInfoBuilder> propertyBuilders;
	private final Map<String, DataSetPropertyInfoImpl> dataSetProperties;
	private final Set<PropertyDependencyInfo> propertyDependencies;
	private final Map<String, MethodElement> eventMethods;
	
	private final PresentationModelErrors errors;

	
	public PresentationModelInfoBuilder(WrappedTypeElement typeElement, 
			String presentationModelObjectTypeName, boolean dataSetPropertyEnabled) {
		this.typeElement = typeElement;
		this.dataSetPropertyEnabled = dataSetPropertyEnabled;
		this.presentationModelObjectTypeName = presentationModelObjectTypeName;
		
		propertyNames = Sets.newHashSet();
		propertyBuilders = Maps.newHashMap();
		dataSetProperties = Maps.newHashMap();
		propertyDependencies = Sets.newHashSet();
		eventMethods = Maps.newHashMap();
		
		errors = new PresentationModelErrors(typeElement.qName());
	}

	public PresentationModelInfo build() {
		List<MethodElement> methods = typeElement.methodsRecursively(new PresentationModelMethodFilter());
		classifyMethods(methods);
		
		processDataSetProperties();
		validateProperties();
		validateDependencies();
		
		errors.assertNoErrors();
		return createPresentationModelInfo();
	}

	private void classifyMethods(List<MethodElement> methods) {
		for(MethodElement method : methods) {
			if(method.isGetter()) {
				addGetter(method.asGetter());
			} else if(method.isSetter()) {
				addSetter(method.asSetter());
			} else if(isEventMethod(method)){//the rest are event methods.
				addEventMethod(method);
			}
		}
		
	}

	private void addGetter(GetterElement getter) {
		String propertyName = getter.propertyName();
		
		if(isDataSetProperty(getter)) {
			ItemPresentationModelAnnotationMirror annotation = new ItemPresentationModelAnnotationMirror(
					getter.getAnnotation(ItemPresentationModel.class));
			DataSetPropertyInfoImpl dataSetProperty = new DataSetPropertyInfoImpl(getter, 
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

	private boolean isDataSetProperty(GetterElement getter) {
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
	
	private void addDependency(String propertyName, GetterElement getter) {
		DependsOnStateOfAnnotationMirror annotation = new DependsOnStateOfAnnotationMirror(
				getter.getAnnotation(DependsOnStateOf.class));
		if(annotation.hasDependentProperty()) {
			PropertyDependencyInfo dependencyInfo = new PropertyDependencyInfo(propertyName, annotation.dependentProperties());
			propertyDependencies.add(dependencyInfo);
		}
	}
	
	private void addSetter(SetterElement setter) {
		String propertyName = setter.propertyName();
		PropertyInfoBuilder propertyBuilder = getFromPropertyBuilders(propertyName);
		propertyBuilder.setSetter(setter);
		
		propertyNames.add(propertyName);
	}

	private boolean isEventMethod(MethodElement method) {
		return method.hasParameter() ? method.firstParameterType().isDeclaredType() : true;
	}
	
	private void addEventMethod(MethodElement method) {
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
		}
		
		removeItemPresentationModelFactoryMethod(dataSetProperties.values());
	}

	private void validateDataSetProperty(DataSetPropertyInfoImpl dataSetProperty) {
		if(!dataSetProperty.isCreatedByFactoryMethod()) {
			return;
		}
		
		String factoryMethodName = dataSetProperty.factoryMethod();
		MethodElement factoryMethod = eventMethods.get(factoryMethodName);
		if((factoryMethod != null) && factoryMethod.hasNoParameter()) {
			WrappedTypeMirror returnType = factoryMethod.returnType();
			if(returnType.isDeclaredType()
					&& ((WrappedDeclaredType)returnType).isAssignableTo(dataSetProperty.itemPresentationModelTypeName())) {
				return;
			}
		}
		
		throw new RuntimeException(MessageFormat.format("The dataSet property ''{0}'' expects an non-existing factory method ''{1}''",
				dataSetProperty.name(), 
				new MethodDescription(factoryMethodName, dataSetProperty.itemPresentationModelTypeName(), new Class<?>[0])));
	}
	
	private void removeSetterOfDataSetProperty(DataSetPropertyInfo dataSetProperty) {
		propertyBuilders.remove(dataSetProperty.name());
	}
	
	private void removeItemPresentationModelFactoryMethod(Collection<DataSetPropertyInfoImpl> dataSetProperties) {
		for(DataSetPropertyInfo dataSetProperty : dataSetProperties) {
			removeItemPresentationModelFactoryMethod(dataSetProperty);
		}
	}
	
	private void removeItemPresentationModelFactoryMethod(DataSetPropertyInfo dataSetProperty) {
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
		return new PresentationModelInfo(typeElement.qName(), presentationModelObjectTypeName,
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
		for(MethodElement method : eventMethods.values()) {
			result.add(new EventMethodInfoImpl(method));
		}
		return result;
	}
}
