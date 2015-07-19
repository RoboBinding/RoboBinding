package org.robobinding.codegen.apt.element;

import java.lang.annotation.Annotation;

import org.robobinding.codegen.apt.Logger;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class WrappedElementAdapter implements WrappedElement {
	private WrappedElement delegate;
	
	public WrappedElementAdapter(WrappedElement delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		return delegate.hasAnnotation(annotationType);
	}

	@Override
	public <A extends Annotation> WrappedAnnotationMirror getAnnotation(Class<A> annotationType) {
		return delegate.getAnnotation(annotationType);
	}

	@Override
	public Logger logger() {
		return delegate.logger();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof WrappedElementAdapter))
			return false;
		
		WrappedElementAdapter that = (WrappedElementAdapter)other;
		return delegate.equals(that.delegate);
	
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}
}
