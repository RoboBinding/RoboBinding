package org.robobinding.codegen;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelInfo {
	
	public Set<String> propertyNames() {
		Set<String> names = Sets.newHashSet();
		for(PropertyInfo propertyInfo : properties()) {
			names.add(propertyInfo.name());
		}
		return names;
	}
	
	public Set<PropertyInfo> properties() {
		return null;
	}
	
	public Set<String> dataSetPropertyNames() {
		Set<String> names = Sets.newHashSet();
		for(DataSetPropertyInfo propertyInfo : dataSetProperties()) {
			names.add(propertyInfo.name());
		}
		return names;
	}
	
	public Set<DataSetPropertyInfo> dataSetProperties() {
		return null;
	}
	
	public Set<PropertyDependencyInfo> propertyDependencies() {
		return null;
	}
	
	public Set<EventMethodInfo> eventMethods() {
		return null;
	}
}
