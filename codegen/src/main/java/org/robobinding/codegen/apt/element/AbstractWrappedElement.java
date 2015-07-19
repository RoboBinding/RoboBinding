package org.robobinding.codegen.apt.element;

import java.lang.annotation.Annotation;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import org.robobinding.codegen.apt.Logger;
import org.robobinding.codegen.apt.MessagerLoggerFactory;
import org.robobinding.codegen.apt.type.TypeMirrorWrapper;
import org.robobinding.codegen.apt.type.WrappedDeclaredType;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public abstract class AbstractWrappedElement implements WrappedElement {
	private final Element element;
	private final TypeMirrorWrapper typeWrapper;
	private final MessagerLoggerFactory loggerFactory;
	
	public AbstractWrappedElement(Element element, 
			TypeMirrorWrapper typeWrapper, MessagerLoggerFactory loggerFactory) {
		this.element = element;
		this.typeWrapper = typeWrapper;
		this.loggerFactory = loggerFactory;
	}

	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		return element.getAnnotation(annotationType) != null;
	}
	
	public <A extends Annotation> WrappedAnnotationMirror getAnnotation(Class<A> annotationType) {
		AnnotationMirror annotationMirror = findAnnotationMirror(annotationType);
		if(annotationMirror != null) {
			return new WrappedAnnotationMirror(annotationMirror, typeWrapper);
		} else {
			throw new RuntimeException("'"+element.toString()+"' is not annotated with @"+annotationType.getName());
		}
	}
	
	private  <A extends Annotation> AnnotationMirror findAnnotationMirror(Class<A> annotationType) {
		for(AnnotationMirror m : element.getAnnotationMirrors()) {
			WrappedDeclaredType annotationDeclaredType = typeWrapper.wrap(m.getAnnotationType());
			if(annotationDeclaredType.isOfType(annotationType)) {
				return m;
			}
		}
		return null;
	}
	
	public Logger logger() {
		return loggerFactory.loggerFor(element);
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof AbstractWrappedElement))
			return false;
	
		AbstractWrappedElement that = (AbstractWrappedElement)other;
		return element.equals(that.element);
	
	}

	@Override
	public int hashCode() {
		return element.hashCode();
	}
}
