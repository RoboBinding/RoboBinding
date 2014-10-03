package org.robobinding.codegen;

import java.util.Set;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyDependencyInfo {
	private final String property;
	private final Set<String> dependentProperties;
	public PropertyDependencyInfo(String property, Set<String> dependentProperties) {
		this.property = property;
		this.dependentProperties = dependentProperties;
	}
	
	public String property() {
		return property;
	}
	
	public Set<String> dependentProperties() {
		return dependentProperties;
	}
}
