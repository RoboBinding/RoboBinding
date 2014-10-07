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
	private final String presentationModelTypeName;
	private final String presentationModelObjectTypeName;
	private final boolean extendsHasPresentationModelChangeSupport;
	private final Set<PropertyInfo> properties;
	private final Set<DataSetPropertyInfo> dataSetProperties;
	private final Set<PropertyDependencyInfo> propertyDependencies;
	private final Set<EventMethodInfo> eventMethods;
	
	public PresentationModelInfo(String presentationModelTypeName, String presentationModelObjectTypeName,
			boolean extendsHasPresentationModelChangeSupport,
			Set<PropertyInfo> properties, Set<DataSetPropertyInfo> dataSetProperties, 
			Set<PropertyDependencyInfo> propertyDependencies, Set<EventMethodInfo> eventMethods) {
		this.presentationModelTypeName = presentationModelTypeName;
		this.presentationModelObjectTypeName = presentationModelObjectTypeName;
		this.extendsHasPresentationModelChangeSupport = extendsHasPresentationModelChangeSupport;
		this.properties = properties;
		this.dataSetProperties = dataSetProperties;
		this.propertyDependencies = propertyDependencies;
		this.eventMethods = eventMethods;
	}

	public Set<String> propertyNames() {
		Set<String> names = Sets.newTreeSet();
		for(PropertyInfo propertyInfo : properties()) {
			names.add(propertyInfo.name());
		}
		return names;
	}
	
	public Set<PropertyInfo> properties() {
		return Collections.unmodifiableSet(properties);
	}
	
	public Set<String> dataSetPropertyNames() {
		Set<String> names = Sets.newTreeSet();
		for(DataSetPropertyInfo propertyInfo : dataSetProperties()) {
			names.add(propertyInfo.name());
		}
		return names;
	}
	
	public Set<DataSetPropertyInfo> dataSetProperties() {
		return Collections.unmodifiableSet(dataSetProperties);
	}
	
	public Set<PropertyDependencyInfo> propertyDependencies() {
		return Collections.unmodifiableSet(propertyDependencies);
	}
	
	public Set<EventMethodInfo> eventMethods() {
		return Collections.unmodifiableSet(eventMethods);
	}

	public String getPresentationModelTypeName() {
		return presentationModelTypeName;
	}

	public String getPresentationModelObjectTypeName() {
		return presentationModelObjectTypeName;
	}
	
	public boolean extendsHasPresentationModelChangeSupport() {
		return extendsHasPresentationModelChangeSupport;
	}
}
