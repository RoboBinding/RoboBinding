package org.robobinding.codegen.processor;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DependsOnStateOfAnnotationMirror {
	private final AnnotationMirrorWrapper annotationMirror;
	
	public DependsOnStateOfAnnotationMirror(AnnotationMirrorWrapper annotationMirror) {
		this.annotationMirror = annotationMirror;
	}
	
	public boolean hasDependentProperty() {
		return annotationMirror.hasAnnotationValue("value") && (dependentProperties().size()>0);
	}
	
	public Set<String> dependentProperties() {
		List<String> rawDependentProperties = annotationMirror.annotationValueAsList("value");
		
		Set<String> dependentProperties = Sets.newHashSet();
		for(String dependentProperty : rawDependentProperties) {
			if(StringUtils.isNotBlank(dependentProperty)) {
				dependentProperties.add(StringUtils.trim(dependentProperty));
			}
		}
		return dependentProperties;
	}

}
