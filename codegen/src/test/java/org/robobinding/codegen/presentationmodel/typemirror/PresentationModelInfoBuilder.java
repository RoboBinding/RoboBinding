package org.robobinding.codegen.presentationmodel.typemirror;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.robobinding.codegen.presentationmodel.DataSetPropertyInfo;
import org.robobinding.codegen.presentationmodel.EventMethodInfo;
import org.robobinding.codegen.presentationmodel.PresentationModelInfo;
import org.robobinding.codegen.presentationmodel.PropertyDependencyInfo;
import org.robobinding.codegen.presentationmodel.PropertyInfo;

import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelInfoBuilder {
	private static final String ITEM_PRESENTATION_MODEL_OBJECT_SUFFIX = "$$IPM";
	
	private final Class<?> presentationModelClass;
	private final String presentationModelObjectTypeName;
	private final boolean dataSetPropertyEnabled;
	
	private final Set<PropertyInfo> properties;
	private final Set<DataSetPropertyInfo> dataSetProperties;
	private final Set<PropertyDependencyInfo> propertyDependencies;
	private final Set<EventMethodInfo> eventMethods;
	
	public PresentationModelInfoBuilder(Class<?> presentationModelClass, String presentationModelObjectTypeName,
			boolean dataSetPropertyEnabled) {
		this.presentationModelClass = presentationModelClass;
		this.presentationModelObjectTypeName = presentationModelObjectTypeName;
		this.dataSetPropertyEnabled = dataSetPropertyEnabled;
		
		properties = Sets.newTreeSet(new PropertyInfoComparator());
		dataSetProperties = Sets.newTreeSet(new DataSetPropertyInfoComparator());
		propertyDependencies = Sets.newTreeSet(new PropertyDependencyInfoComparator());
		eventMethods = Sets.newTreeSet(new EventMethodInfoComparator());
	}

	public PresentationModelInfo build() {
		buildProperties();
		buildEventMethods();

		return new PresentationModelInfo(presentationModelClass.getName(), presentationModelObjectTypeName(),
				properties, dataSetProperties, propertyDependencies, eventMethods);
	}

	
	private void buildProperties() {
		Set<PropertyDescriptor> descriptors = PropertyDescriptorUtils.getPropertyDescriptors(presentationModelClass);
		for(PropertyDescriptor descriptor : descriptors) {
			if(isDataSetProperty(descriptor)) {
				ItemPresentationModel itemPresentationModelAnnotation = descriptor.getAnnotation(ItemPresentationModel.class);
				DataSetPropertyInfoImpl dataSetPropertyInfo = new DataSetPropertyInfoImpl(descriptor, itemPresentationModelAnnotation,
						itemPresentationModelObjectTypeNameOf(itemPresentationModelAnnotation));
				if (dataSetPropertyInfo.isCreatedByFactoryMethod()) {
					String factoryMethodReturnType = null;
					try {
						Method method = presentationModelClass.getMethod(itemPresentationModelAnnotation.factoryMethod(), Object.class);
						dataSetPropertyInfo.setFactoryMethodHasArg(true);
						factoryMethodReturnType = method.getReturnType().getName();
					} catch (NoSuchMethodException e) {
						dataSetPropertyInfo.setFactoryMethodHasArg(false);
						try {
							Method method = presentationModelClass.getMethod(itemPresentationModelAnnotation.factoryMethod());
							factoryMethodReturnType = method.getReturnType().getName();
						} catch (NoSuchMethodException e1) {
						}
					}
					dataSetPropertyInfo.setFactoryMethodReturnTypeName(factoryMethodReturnType);
				}
				dataSetProperties.add(dataSetPropertyInfo);
			} else {
				properties.add(new PropertyInfoImpl(descriptor));
			}
			
			DependsOnStateOf dependsOnStateOfAnnotation = descriptor.getAnnotation(DependsOnStateOf.class);
			Set<String> dependentProperties = dependentProperties(dependsOnStateOfAnnotation);
			if(!dependentProperties.isEmpty()) {
				PropertyDependencyInfo dependencyInfo = new PropertyDependencyInfo(descriptor.getName(), dependentProperties);
				propertyDependencies.add(dependencyInfo);
			}
		}
	}
	
	private boolean isDataSetProperty(PropertyDescriptor descriptor) {
		return dataSetPropertyEnabled && descriptor.hasAnnotation(ItemPresentationModel.class);
	}
	
	private String itemPresentationModelObjectTypeNameOf(ItemPresentationModel itemPresentationModelAnnotation) {
		return itemPresentationModelAnnotation.value().getName() + ITEM_PRESENTATION_MODEL_OBJECT_SUFFIX;
	}
	
	private Set<String> dependentProperties(DependsOnStateOf dependsOnStateOfAnnotation) {
		if((dependsOnStateOfAnnotation == null)
				|| ArrayUtils.isEmpty(dependsOnStateOfAnnotation.value())) {
			return Collections.emptySet();
		}
		
		Set<String> dependentProperties = Sets.newTreeSet();
		for(String dependentProperty : dependsOnStateOfAnnotation.value()) {
			if(StringUtils.isNotBlank(dependentProperty)) {
				dependentProperties.add(StringUtils.trim(dependentProperty));
			}
		}
		return dependentProperties;
	}

	private void buildEventMethods() {
		for(Method method : presentationModelClass.getMethods()) {
			if(isEventMethod(method)) {
				eventMethods.add(new EventMethodInfoImpl(method));
			}
		}
	}

	private boolean isEventMethod(Method method) {
		return method.getAnnotation(Event.class) != null;
	}
	
	private String presentationModelObjectTypeName() {
		return presentationModelObjectTypeName;
	}
}
