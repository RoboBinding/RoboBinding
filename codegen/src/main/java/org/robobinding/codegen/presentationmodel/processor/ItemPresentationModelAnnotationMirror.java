package org.robobinding.codegen.presentationmodel.processor;

import org.robobinding.codegen.apt.element.WrappedAnnotationMirror;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ItemPresentationModelAnnotationMirror {
	private static final String FACTORY_METHOD = "factoryMethod";
	private static final String VIEW_TYPE_SELECTOR = "viewTypeSelector";
	
	private final WrappedAnnotationMirror annotationMirror;
	public ItemPresentationModelAnnotationMirror(WrappedAnnotationMirror annotationMirror) {
		this.annotationMirror = annotationMirror;
	}
	
	public String itemPresentationModelTypeName() {
		return annotationMirror.annotationValueAsType("value").className();
	}
	
	public boolean hasFactoryMethod() {
		return annotationMirror.hasAnnotationValue(FACTORY_METHOD);
	}

	public String factoryMethod() {
		return annotationMirror.annotationValueAsText(FACTORY_METHOD);
	}

	public boolean hasViewTypeSelector() {
		return annotationMirror.hasAnnotationValue(VIEW_TYPE_SELECTOR);
	}
	
	public String viewTypeSelector() {
		return annotationMirror.annotationValueAsText(VIEW_TYPE_SELECTOR);
	}
}
