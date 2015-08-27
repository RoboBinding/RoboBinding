package org.robobinding.codegen.presentationmodel.processor;

import org.robobinding.codegen.apt.element.WrappedAnnotationMirror;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ItemPresentationModelAnnotationMirror {
	private final WrappedAnnotationMirror annotationMirror;
	public ItemPresentationModelAnnotationMirror(WrappedAnnotationMirror annotationMirror) {
		this.annotationMirror = annotationMirror;
	}
	
	public String itemPresentationModelTypeName() {
		return annotationMirror.annotationValueAsType("value").className();
	}

	public boolean hasItemViewFactory() {
		return annotationMirror.hasAnnotationValue("viewFactory");
	}

	public String itemViewFactoryTypeName() {
		return annotationMirror.annotationValueAsType("viewFactory").className();
	}
	
	public boolean hasFactoryMethod() {
		return annotationMirror.hasAnnotationValue("factoryMethod");
	}

	public String factoryMethod() {
		return annotationMirror.annotationValueAsText("factoryMethod");
	}
}
