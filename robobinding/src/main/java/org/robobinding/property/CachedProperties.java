package org.robobinding.property;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CachedProperties implements Properties {
    private PropertyCreator propertyCreator;
    private Map<String, PropertyValueModel<?>> propertyCache;
    private Map<String, DataSetPropertyValueModel<?>> dataSetPropertyCache;

    public CachedProperties(PropertyCreator propertyCreator) {
	this.propertyCreator = propertyCreator;
	propertyCache = Maps.newHashMap();
	dataSetPropertyCache = Maps.newHashMap();
    }

    @Override
    public Class<?> getPropertyType(String propertyName) {
	PropertyValueModel<?> property = getProperty(propertyName, false);
	return property.getPropertyType();
    }

    @Override
    public <T> ValueModel<T> getReadWriteProperty(String propertyName) {
	return getProperty(propertyName, true);
    }

    @Override
    public <T> ValueModel<T> getReadOnlyProperty(String propertyName) {
	return getProperty(propertyName, false);
    }

    private <T> PropertyValueModel<T> getProperty(String propertyName, boolean isReadWriteProperty) {
	@SuppressWarnings("unchecked")
	PropertyValueModel<T> property = (PropertyValueModel<T>) propertyCache.get(propertyName);
	if (property == null) {
	    property = propertyCreator.createProperty(propertyName);
	    propertyCache.put(propertyName, property);
	}
	property.checkReadWriteProperty(isReadWriteProperty);
	return property;
    }

    @Override
    public <T> DataSetValueModel<T> getDataSetProperty(String propertyName) {
	@SuppressWarnings("unchecked")
	DataSetPropertyValueModel<T> dataSetProperty = (DataSetPropertyValueModel<T>) dataSetPropertyCache.get(propertyName);
	if (dataSetProperty == null) {
	    dataSetProperty = propertyCreator.createDataSetProperty(propertyName);
	    dataSetPropertyCache.put(propertyName, dataSetProperty);
	}
	dataSetProperty.checkReadWriteProperty(false);
	return dataSetProperty;
    }
}
