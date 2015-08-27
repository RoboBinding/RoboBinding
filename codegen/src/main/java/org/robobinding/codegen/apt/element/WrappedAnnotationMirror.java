package org.robobinding.codegen.apt.element;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import org.robobinding.codegen.apt.type.TypeMirrorWrapper;
import org.robobinding.codegen.apt.type.WrappedDeclaredType;

import com.google.common.collect.Lists;

public class WrappedAnnotationMirror {
	private final TypeMirrorWrapper wrapper;
	private final AnnotationMirror annotationMirror;

	public WrappedAnnotationMirror(AnnotationMirror annotationMirror, TypeMirrorWrapper wrapper) {
		this.annotationMirror = annotationMirror;
		this.wrapper = wrapper;
	}

	public WrappedDeclaredType annotationValueAsType(String key) {
		AnnotationValue annotationValue = findAnnotationValue(key);
		if (annotationValue == null) {
			return null;
		}

		return wrapper.wrap((TypeMirror) annotationValue.getValue());
	}

	private AnnotationValue findAnnotationValue(String key) {
		for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
			if (entry.getKey().getSimpleName().toString().equals(key)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public String annotationValueAsText(String key) {
		AnnotationValue annotationValue = findAnnotationValue(key);
		if (annotationValue == null) {
			return null;
		}

		return (String) annotationValue.getValue();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> annotationValueAsList(String key) {
		AnnotationValue annotationValue = findAnnotationValue(key);
		if (annotationValue == null) {
			return null;
		}

		List<? extends AnnotationValue> annotationValues = (List<? extends AnnotationValue>) annotationValue.getValue();
		List<T> result = Lists.newArrayList();
		for (AnnotationValue innerAnnotationValue : annotationValues) {
			result.add((T) innerAnnotationValue.getValue());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<WrappedAnnotationMirror> annotationValueAsAnnotationList(String key) {
		AnnotationValue annotationValue = findAnnotationValue(key);
		if (annotationValue == null) {
			return null;
		}

		List<? extends AnnotationValue> annotationValues = (List<? extends AnnotationValue>) annotationValue.getValue();
		List<WrappedAnnotationMirror> result = Lists.newArrayList();
		for (AnnotationValue innerAnnotationValue : annotationValues) {
			AnnotationMirror annotationMirror = (AnnotationMirror) innerAnnotationValue.getValue();
			result.add(new WrappedAnnotationMirror(annotationMirror, wrapper));
		}
		return result;
	}

	public boolean hasAnnotationValue(String key) {
		return findAnnotationValue(key) != null;
	}

}
