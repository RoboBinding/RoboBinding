package org.robobinding.codegen;

import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelInfo {
	private final Class<?> presentationModelClass;
	private final String presentationModelObjectTypeName;
	private final Set<PropertyInfoForTest> properties;
	private final Set<DataSetPropertyInfoForTest> dataSetProperties;
	private final Set<PropertyDependencyInfo> propertyDependencies;
	private final Set<EventMethodInfo> eventMethods;
	
	public PresentationModelInfo(Class<?> presentationModelClass, String presentationModelObjectTypeName,
			Set<PropertyInfoForTest> properties, Set<DataSetPropertyInfoForTest> dataSetProperties, 
			Set<PropertyDependencyInfo> propertyDependencies, Set<EventMethodInfo> eventMethods) {
		this.presentationModelClass = presentationModelClass;
		this.presentationModelObjectTypeName = presentationModelObjectTypeName;
		this.properties = properties;
		this.dataSetProperties = dataSetProperties;
		this.propertyDependencies = propertyDependencies;
		this.eventMethods = eventMethods;
	}

	public Set<String> propertyNames() {
		Set<String> names = Sets.newHashSet();
		for(PropertyInfoForTest propertyInfo : properties()) {
			names.add(propertyInfo.name());
		}
		return names;
	}
	
	public Set<PropertyInfoForTest> properties() {
		return Collections.unmodifiableSet(properties);
	}
	
	public Set<String> dataSetPropertyNames() {
		Set<String> names = Sets.newHashSet();
		for(DataSetPropertyInfoForTest propertyInfo : dataSetProperties()) {
			names.add(propertyInfo.name());
		}
		return names;
	}
	
	public Set<DataSetPropertyInfoForTest> dataSetProperties() {
		return Collections.unmodifiableSet(dataSetProperties);
	}
	
	public Set<PropertyDependencyInfo> propertyDependencies() {
		return Collections.unmodifiableSet(propertyDependencies);
	}
	
	public Set<EventMethodInfo> eventMethods() {
		return Collections.unmodifiableSet(eventMethods);
	}

	public Class<?> getPresentationModelClass() {
		return presentationModelClass;
	}

	public String getPresentationModelObjectTypeName() {
		return presentationModelObjectTypeName;
	}
}
