package org.robobinding.codegen;

import java.lang.reflect.Method;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.robobinding.annotation.DependsOnStateOf;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.function.MethodDescription;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelInfoBuilder extends AbstractPresentationModelInfoBuilder {
	private static final String ITEM_PRESENTATION_MODEL_OBJECT_SUFFIX = "$$IPM";
	public PresentationModelInfoBuilder(Class<?> presentationModelClass, String presentationModelObjectTypeName) {
		super(presentationModelClass, presentationModelObjectTypeName);
	}

	@Override
	public void buildProperties() {
		Set<PropertyDescriptor> descriptors = PropertyDescriptorUtils.getPropertyDescriptors(presentationModelClass);
		for(PropertyDescriptor descriptor : descriptors) {
			if(isDataSetProperty(descriptor)) {
				validateDataSetProperty(descriptor);
				ItemPresentationModel itemPresentationModelAnnotation = descriptor.getAnnotation(ItemPresentationModel.class);
				dataSetProperties.add(new DataSetPropertyInfo(descriptor, itemPresentationModelAnnotation, 
						itemPresentationModelObjectTypeNameOf(itemPresentationModelAnnotation)));
			} else {
				properties.add(new PropertyInfo(descriptor));
			}
			
			DependsOnStateOf dependsOnStateOfAnnotation = descriptor.getAnnotation(DependsOnStateOf.class);
			Set<String> dependentProperties = dependentProperties(dependsOnStateOfAnnotation);
			if(!dependentProperties.isEmpty()) {
				validation.validate(descriptor, dependentProperties);
				PropertyDependencyInfo dependencyInfo = new PropertyDependencyInfo(descriptor.getName(), dependentProperties);
				propertyDependencies.add(dependencyInfo);
			}
		}
	}
	
	private boolean isDataSetProperty(PropertyDescriptor descriptor) {
		return descriptor.hastAnnotation(ItemPresentationModel.class);
	}
	
	private void validateDataSetProperty(PropertyDescriptor descriptor) {
		ItemPresentationModel itemPresentationModelAnnotation = descriptor.getAnnotation(ItemPresentationModel.class);
		String factoryMethodName = StringUtils.trim(itemPresentationModelAnnotation.factoryMethod());
		if(StringUtils.isNotBlank(factoryMethodName)) {
			Method factoryMethod = MethodUtils.getAccessibleMethod(presentationModelClass, factoryMethodName, new Class<?>[0]);
			if(factoryMethod == null) {
				throw new RuntimeException("No such factory method " + new MethodDescription(
						presentationModelClass, factoryMethodName, new Class<?>[0]));
			}
		}
	}
	
	private String itemPresentationModelObjectTypeNameOf(ItemPresentationModel itemPresentationModelAnnotation) {
		return itemPresentationModelAnnotation.value().getName() + ITEM_PRESENTATION_MODEL_OBJECT_SUFFIX;
	}
}
