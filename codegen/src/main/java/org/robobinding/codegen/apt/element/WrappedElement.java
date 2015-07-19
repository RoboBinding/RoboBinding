package org.robobinding.codegen.apt.element;

import java.lang.annotation.Annotation;

import org.robobinding.codegen.apt.Logger;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface WrappedElement {
	boolean hasAnnotation(Class<? extends Annotation> annotationType);
	
	<A extends Annotation> WrappedAnnotationMirror getAnnotation(Class<A> annotationType);
	
	Logger logger();
}
