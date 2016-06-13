package org.robobinding.codegen.apt.element;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import org.robobinding.codegen.apt.type.TypeMirrorWrapper;
import org.robobinding.codegen.apt.type.WrappedDeclaredType;
import org.robobinding.util.Lists;

public class WrappedAnnotationMirror {
	private final AnnotationMirror annotationMirror;
	private final Map<? extends ExecutableElement, ? extends AnnotationValue> withDefaultValues;
	private final TypeMirrorWrapper wrapper;

	public WrappedAnnotationMirror(AnnotationMirror annotationMirror,
			Map<? extends ExecutableElement, ? extends AnnotationValue> withDefaultValues, 
			TypeMirrorWrapper wrapper) {
		this.annotationMirror = annotationMirror;
		this.withDefaultValues = withDefaultValues;
		this.wrapper = wrapper;
	}

	public WrappedDeclaredType annotationValueAsType(String key) {
		AnnotationValue annotationValue = findWithDefaltValues(key);
		if (annotationValue == null) {
			return null;
		}

		return wrapper.wrap((TypeMirror) annotationValue.getValue());
	}

	private AnnotationValue find(String key) {
		
		for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
			if (entry.getKey().getSimpleName().toString().equals(key)) {
				return entry.getValue();
			}
		}
		return null;
	}

	private AnnotationValue findWithDefaltValues(String key) {
		
		for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : withDefaultValues.entrySet()) {
			if (entry.getKey().getSimpleName().toString().equals(key)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	

	public String annotationValueAsText(String key) {
		AnnotationValue annotationValue = findWithDefaltValues(key);
		if (annotationValue == null) {
			return null;
		}

		return (String) annotationValue.getValue();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> annotationValueAsList(String key) {
		AnnotationValue annotationValue = findWithDefaltValues(key);
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

	public boolean hasAnnotationValue(String key) {
		return find(key) != null;
	}
	
	public <T extends Enum<T>> T annotationValueAsEnum(Class<T> type, String key) {
		AnnotationValue annotationValue = findWithDefaltValues(key);
		if (annotationValue == null) {
			return null;
		}
		
		return Enum.valueOf(type, annotationValue.getValue().toString());
	}

}
