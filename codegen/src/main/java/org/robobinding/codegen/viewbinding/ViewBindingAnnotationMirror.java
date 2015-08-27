package org.robobinding.codegen.viewbinding;

import java.util.List;

import org.robobinding.codegen.apt.element.WrappedAnnotationMirror;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingAnnotationMirror {
	private final WrappedAnnotationMirror annotationMirror;
	
	public ViewBindingAnnotationMirror(WrappedAnnotationMirror annotationMirror) {
		this.annotationMirror = annotationMirror;
	}
	
	public List<String> getSimpleOneWayProperties() {
		List<String> simpleOneWayProperties = annotationMirror.annotationValueAsList("simpleOneWayProperties");
		if(simpleOneWayProperties == null) {
			return Lists.newArrayList();
		}
		return simpleOneWayProperties;
	}

	public List<WrappedAnnotationMirror> getTwoWayProperties() {
		List<WrappedAnnotationMirror> twoWayProperties = annotationMirror.annotationValueAsAnnotationList("twoWayProperties");
		if (twoWayProperties == null) {
			return Lists.newArrayList();
		}
		return twoWayProperties;
	}

}
