package org.robobinding.codegen.presentationmodel.processor;

import org.robobinding.annotation.PreInitializingViews;
import org.robobinding.codegen.apt.element.WrappedAnnotationMirror;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ItemPresentationModelAnnotationMirror {
	private static final String FACTORY_METHOD = "factoryMethod";
	private static final String VIEW_TYPE_SELECTOR = "viewTypeSelector";
	private static final String PRE_INITIALIZING_VIEWS = "preInitializingViews";
	
	private final WrappedAnnotationMirror annotationMirror;
	public ItemPresentationModelAnnotationMirror(WrappedAnnotationMirror annotationMirror) {
		this.annotationMirror = annotationMirror;
	}
	
	public String itemPresentationModelTypeName() {
		return annotationMirror.annotationValueAsType("value").className();
	}
	
	public String itemPresentationModelTypeBinaryName() {
		return annotationMirror.annotationValueAsType("value").binaryName();
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

	public PreInitializingViews preInitializingViews() {
		return annotationMirror.annotationValueAsEnum(PreInitializingViews.class, PRE_INITIALIZING_VIEWS);
	}
}
