package org.robobinding.codegen.presentationmodel.processor;

import java.text.MessageFormat;
import java.util.Map;

import org.robobinding.codegen.apt.element.GetterElement;
import org.robobinding.codegen.apt.element.MethodElement;
import org.robobinding.codegen.apt.type.WrappedTypeMirror;
import org.robobinding.codegen.presentationmodel.DataSetPropertyInfo;
import org.robobinding.itempresentationmodel.ViewTypeSelectionContext;

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
	
	public void supplyRequiredMethodsFrom(Map<String, MethodElement> availableMethods) {
		if(annotation.hasFactoryMethod()) {
			addFactoryMethodFrom(availableMethods);
		}
		
		if(annotation.hasViewTypeSelector()) {
			addViewTypeSelectorFrom(availableMethods);
		}
	}
	
	private void addFactoryMethodFrom(Map<String, MethodElement> availableMethods) {
		String factoryMethodName = annotation.factoryMethod();
		factoryMethod = availableMethods.get(factoryMethodName);
		checkFactoryMethod();
	}
	
	private void checkFactoryMethod() {
		if((factoryMethod != null) && 
				(factoryMethod.hasNoParameter() || factoryMethod.hasSingleParameterTyped(int.class))) {
			WrappedTypeMirror returnType = factoryMethod.returnType();
			if(returnType.isAssignableTo(annotation.itemPresentationModelTypeName())) {
				return;
			}
		}
		
		throw new RuntimeException(MessageFormat.format("The dataSet property ''{0}'' expects an non-existing factory method ''{1}'' or ''{2}''",
				propertyName(), 
				new MethodDescription(annotation.factoryMethod(), annotation.itemPresentationModelTypeName(), new Class<?>[0]),
				new MethodDescription(annotation.factoryMethod(), annotation.itemPresentationModelTypeName(), new Class<?>[]{int.class})));
	}

	private void addViewTypeSelectorFrom(Map<String, MethodElement> availableMethods) {
		String viewTypeSelectorName = annotation.viewTypeSelector();
		viewTypeSelector = availableMethods.get(viewTypeSelectorName);
		checkViewTypeSelector();
	}

	private void checkViewTypeSelector() {
		if((viewTypeSelector != null) && viewTypeSelector.hasSingleParameterTyped(ViewTypeSelectionContext.class)) {
			WrappedTypeMirror returnType = viewTypeSelector.returnType();
			if(returnType.isAssignableTo(int.class)) {
				return;
			}
		}
		
		throw new RuntimeException(MessageFormat.format("The dataSet property ''{0}'' expects an non-existing viewTypeSelector ''{1}''",
				propertyName(), 
				new MethodDescription(annotation.viewTypeSelector(), "int", new Class<?>[]{ViewTypeSelectionContext.class})));
	}
	
	public String propertyName() {
		return getter.propertyName();
	}
	
	public String factoryMethod() {
		return annotation.factoryMethod();
	}
	
	public String viewTypeSelector() {
		return annotation.viewTypeSelector();
	}

	public DataSetPropertyInfo build() {
		return new DataSetPropertyInfoImpl(getter, annotation.itemPresentationModelTypeName(), itemPresentationModelObjectTypeName, 
				factoryMethod, viewTypeSelector, annotation.preInitializingViews());
	}
}
