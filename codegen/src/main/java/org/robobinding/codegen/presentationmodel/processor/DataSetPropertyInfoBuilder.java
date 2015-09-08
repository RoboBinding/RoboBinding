package org.robobinding.codegen.presentationmodel.processor;

import org.robobinding.codegen.apt.element.GetterElement;
import org.robobinding.codegen.apt.element.MethodElement;
import org.robobinding.codegen.presentationmodel.DataSetPropertyInfo;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DataSetPropertyInfoBuilder {
	private final GetterElement getter;
	private final ItemPresentationModelAnnotationMirror annotation;
	private final String itemPresentationModelObjectTypeName;
	private MethodElement factoryMethod;
	private MethodElement viewTypeSelector;
	
	public DataSetPropertyInfoBuilder(GetterElement getter, 
			ItemPresentationModelAnnotationMirror annotation,
			String itemPresentationModelObjectTypeName) {
		this.getter = getter;
		this.annotation = annotation;
		this.itemPresentationModelObjectTypeName = itemPresentationModelObjectTypeName;
	}
	
	public void setFactoryMethod(MethodElement factoryMethod) {
		this.factoryMethod = factoryMethod;
	}
	
	public void setViewTypeSelector(MethodElement viewTypeSelector) {
		this.viewTypeSelector = viewTypeSelector;
	}
	
	public DataSetPropertyInfo build() {
		return new DataSetPropertyInfoImpl(getter, annotation, itemPresentationModelObjectTypeName, factoryMethod, viewTypeSelector);
	}
}
