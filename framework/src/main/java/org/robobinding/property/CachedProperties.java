package org.robobinding.property;

import java.util.Map;

import org.robobinding.internal.guava.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CachedProperties implements Properties {
    private final PropertiesWithDependency properties;
    private final Map<String, PropertyValueModel> propertyCache;
    private final Map<String, DataSetPropertyValueModel> dataSetPropertyCache;

    public CachedProperties(PropertiesWithDependency properties) {
	this.properties = properties;
	propertyCache = Maps.newHashMap();
	dataSetPropertyCache = Maps.newHashMap();
    }

    @Override
    public Class<?> getPropertyType(String propertyName) {
	PropertyValueModel property = getProperty(propertyName, false);
	return property.getPropertyType();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ValueModel<T> getReadWriteProperty(String propertyName) {
	return (ValueModel<T>) getProperty(propertyName, true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ValueModel<T> getReadOnlyProperty(String propertyName) {
	return (ValueModel<T>) getProperty(propertyName, false);
    }

    private PropertyValueModel getProperty(String propertyName, boolean isReadWriteProperty) {
	PropertyValueModel property = propertyCache.get(propertyName);
	if (property == null) {
	    property = properties.createProperty(propertyName);
	    propertyCache.put(propertyName, property);
	}
	property.checkReadWriteProperty(isReadWriteProperty);
	return property;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> DataSetValueModel<T> getDataSetProperty(String propertyName) {
	DataSetPropertyValueModel dataSetProperty = dataSetPropertyCache.get(propertyName);
	if (dataSetProperty == null) {
	    dataSetProperty = properties.createDataSetProperty(propertyName);
	    dataSetPropertyCache.put(propertyName, dataSetProperty);
	}
	dataSetProperty.checkReadWriteProperty(false);
	return (DataSetValueModel<T>) dataSetProperty;
    }
}
