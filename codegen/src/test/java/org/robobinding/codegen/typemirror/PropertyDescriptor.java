package org.robobinding.codegen.typemirror;

import java.lang.annotation.Annotation;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class PropertyDescriptor {
	private final org.robobinding.internal.java_beans.PropertyDescriptor descriptor;

	public PropertyDescriptor(org.robobinding.internal.java_beans.PropertyDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	public Class<?> getPropertyType() {
		return descriptor.getPropertyType();
	}

	public String getName() {
		return descriptor.getName();
	}

	public String getReadMethodName() {
		return descriptor.getReadMethod().getName();
	}

	public boolean isReadable() {
		return descriptor.getReadMethod() != null;
	}

	public String getWriteMethodName() {
		return descriptor.getWriteMethod().getName();
	}

	public boolean isWritable() {
		return descriptor.getWriteMethod() != null;
	}

	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		if(isReadable()) {
			return descriptor.getReadMethod().getAnnotation(annotationClass);
		} else {
			return null;
		}
	}

	public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
		return getAnnotation(annotationClass) != null;
	}
}
