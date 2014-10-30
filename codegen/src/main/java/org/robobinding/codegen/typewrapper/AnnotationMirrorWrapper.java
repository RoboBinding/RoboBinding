package org.robobinding.codegen.typewrapper;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class AnnotationMirrorWrapper {
	private final ProcessingContext context;
	private final AnnotationMirror annotationMirror;
	
	public AnnotationMirrorWrapper(ProcessingContext context, AnnotationMirror annotationMirror) {
		this.context = context;
		this.annotationMirror = annotationMirror;
	}

	public AbstractTypeElementWrapper annotationValueAsType(String key) {
		AnnotationValue annotationValue = findAnnotationValue(key);
		if(annotationValue == null) {
			return null;
		}
		
		return context.typeElementOf((TypeMirror)annotationValue.getValue());
	}
	
	private AnnotationValue findAnnotationValue(String key) {
		for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
			if(entry.getKey().getSimpleName().toString().equals(key)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public String annotationValueAsText(String key) {
		AnnotationValue annotationValue = findAnnotationValue(key);
		if(annotationValue == null) {
			return null;
		}
		
		return (String)annotationValue.getValue();
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> annotationValueAsList(String key) {
		AnnotationValue annotationValue = findAnnotationValue(key);
		if(annotationValue == null) {
			return null;
		}
		
		List<? extends AnnotationValue> annotationValues = (List<? extends AnnotationValue>)annotationValue.getValue();
		List<T> result = Lists.newArrayList();
		for(AnnotationValue innerAnnotationValue : annotationValues) {
			result.add((T)innerAnnotationValue.getValue());
		}
		return result;
	}

	public boolean hasAnnotationValue(String key) {
		return findAnnotationValue(key) != null;
	}
}
