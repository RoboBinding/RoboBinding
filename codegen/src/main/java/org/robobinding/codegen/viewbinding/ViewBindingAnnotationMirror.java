package org.robobinding.codegen.viewbinding;

import java.util.List;

import org.robobinding.codegen.typewrapper.AnnotationMirrorWrapper;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingAnnotationMirror {
	private final AnnotationMirrorWrapper annotationMirror;
	
	public ViewBindingAnnotationMirror(AnnotationMirrorWrapper annotationMirror) {
		this.annotationMirror = annotationMirror;
	}
	
	public List<String> getSimpleOneWayProperties() {
		List<String> simpleOneWayProperties = annotationMirror.annotationValueAsList("simpleOneWayProperties");
		if(simpleOneWayProperties == null) {
			return Lists.newArrayList();
		}
		return simpleOneWayProperties;
	}
}
