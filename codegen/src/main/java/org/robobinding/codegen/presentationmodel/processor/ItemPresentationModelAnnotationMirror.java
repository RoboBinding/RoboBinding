package org.robobinding.codegen.presentationmodel.processor;

import org.robobinding.codegen.typewrapper.AnnotationMirrorWrapper;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ItemPresentationModelAnnotationMirror {
	private final AnnotationMirrorWrapper annotationMirror;
	public ItemPresentationModelAnnotationMirror(AnnotationMirrorWrapper annotationMirror) {
		this.annotationMirror = annotationMirror;
	}
	
	public String itemPresentationModelTypeName() {
		return annotationMirror.annotationValueAsType("value").typeName();
	}
	
	public boolean hasFactoryMethod() {
		return annotationMirror.hasAnnotationValue("factoryMethod");
	}

	public String factoryMethod() {
		return annotationMirror.annotationValueAsText("factoryMethod");
	}
}
