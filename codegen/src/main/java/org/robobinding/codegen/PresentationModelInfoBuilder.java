package org.robobinding.codegen;

import java.lang.reflect.Method;
import java.util.Map;

import javax.lang.model.element.ExecutableElement;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.function.MethodDescription;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelInfoBuilder extends AbstractPresentationModelInfoBuilder {
	private Map<String, PropertyInfoBuilder> propertyBuilders;
	private Map<String, DataSetPropertyInfo> dataSetProperties;

	@Override
	protected void addGetter(MethodElementWrapper getter) {
		if(isDataSetProperty(getter)) {
			ItemPresentationModel itemPresentationModelAnnotation = descriptor.getAnnotation(ItemPresentationModel.class);
			dataSetProperties.add(new DataSetPropertyInfo(descriptor, itemPresentationModelAnnotation, 
					itemPresentationModelObjectTypeNameOf(itemPresentationModelAnnotation)));
		}

	}
	
	private boolean isDataSetProperty(MethodElementWrapper getter) {
		return getter.hasAnnotation(ItemPresentationModel.class);
	}
	
	private void validateDataSetProperty(ExecutableElement getter) {
		ItemPresentationModel itemPresentationModelAnnotation = getter.getAnnotation(ItemPresentationModel.class);
		String factoryMethodName = StringUtils.trim(itemPresentationModelAnnotation.factoryMethod());
		if(StringUtils.isNotBlank(factoryMethodName)) {
			Method factoryMethod = MethodUtils.getAccessibleMethod(presentationModelClass, factoryMethodName, new Class<?>[0]);
			if(factoryMethod == null) {
				throw new RuntimeException("No such factory method " + new MethodDescription(
						presentationModelClass, factoryMethodName, new Class<?>[0]));
			}
		}
	}
	
	@Override
	protected void addSetter(ExecutableElement method) {
		// TODO Auto-generated method stub

	}

}
