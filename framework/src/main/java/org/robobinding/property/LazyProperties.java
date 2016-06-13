package org.robobinding.property;

import java.util.Map;
import java.util.Set;

import org.robobinding.util.Maps;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class LazyProperties implements Properties {
	private final Class<?> beanClass;
	private final Map<String, PropertyValueModel> properties;
	private final Map<String, DataSetPropertyValueModel> dataSetProperties;
	
	private final PropertyWithDependencySupply supply;
	
	public LazyProperties(Class<?> beanClass, Set<String> propertyNames, Set<String> dataSetPropertyNames, 
			PropertyWithDependencySupply supply) {
		this.beanClass = beanClass;
		this.supply = supply;
		properties = Maps.newHashMap();
		dataSetProperties = Maps.newHashMap();
		
		initializeProperties(propertyNames);
		initializeDataSetProperties(dataSetPropertyNames);
	}


	private void initializeProperties(Set<String> propertyNames) {
		for(String propertyName : propertyNames) {
			properties.put(propertyName, null);
		}
	}

	private void initializeDataSetProperties(Set<String> dataSetPropertyNames) {
		for(String propertyName : dataSetPropertyNames) {
			dataSetProperties.put(propertyName, null);
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> ValueModel<T> getReadOnlyProperty(String propertyName) {
		return (ValueModel<T>) getProperty(propertyName, false);
	}

	private PropertyValueModel getProperty(String propertyName, boolean isReadWriteProperty) {
		if(!properties.containsKey(propertyName)) {
			throw new RuntimeException("No such property '"+describeProperty(propertyName)+"'");
		}
		
		PropertyValueModel property = properties.get(propertyName);
		if (property == null) {
			properties.put(propertyName, supply.createProperty(propertyName));
			property = properties.get(propertyName);
		}
		
		property.checkReadWriteProperty(isReadWriteProperty);
		return property;
	}

	private String describeProperty(String propertyName) {
		return PropertyUtils.shortDescription(beanClass, propertyName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ValueModel<T> getReadWriteProperty(String propertyName) {
		return (ValueModel<T>) getProperty(propertyName, true);
	}

	@Override
	public Class<?> getPropertyType(String propertyName) {
		PropertyValueModel property = getProperty(propertyName, false);
		return property.getPropertyType();
	}
	
	@Override
	public DataSetValueModel getDataSetProperty(String propertyName) {
		if(!dataSetProperties.containsKey(propertyName)) {
			throw new RuntimeException("No such dataSet property '"+describeProperty(propertyName)+"'");
		}
		
		DataSetPropertyValueModel property = dataSetProperties.get(propertyName);
		if (property == null) {
			dataSetProperties.put(propertyName, supply.createDataSetProperty(propertyName));
			property = dataSetProperties.get(propertyName);
		}
		return property;
	}
}
