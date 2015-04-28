package org.robobinding.property;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class LazyProperties implements Properties {
	private final Class<?> beanClass;
	private final Map<String, PropertyValueModel> properties;
	private final Map<String, DataSetPropertyValueModel> dataSetProperties;
    private final Map<String, GroupedDataSetPropertyValueModel> groupedDataSetProperties;
	
	private final PropertyWithDependencySupply supply;
	
	public LazyProperties(Class<?> beanClass, Set<String> propertyNames, Set<String> dataSetPropertyNames,
                          Set<String> groupedDataSetPropertyNames, PropertyWithDependencySupply supply) {
		this.beanClass = beanClass;
		this.supply = supply;
		properties = Maps.newHashMap();
		dataSetProperties = Maps.newHashMap();
		groupedDataSetProperties = Maps.newHashMap();

		initializeProperties(propertyNames);
		initializeDataSetProperties(dataSetPropertyNames);
        initializeGroupedDataSetProperties(groupedDataSetPropertyNames);
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

    private void initializeGroupedDataSetProperties(Set<String> groupedDataSetPropertyNames) {
        for(String propertyName : groupedDataSetPropertyNames) {
            groupedDataSetProperties.put(propertyName, null);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> DataSetValueModel<T> getDataSetProperty(String propertyName) {
		if(!dataSetProperties.containsKey(propertyName)) {
			throw new RuntimeException("No such dataSet property '"+describeProperty(propertyName)+"'");
		}
		
		DataSetPropertyValueModel property = dataSetProperties.get(propertyName);
		if (property == null) {
			dataSetProperties.put(propertyName, supply.createDataSetProperty(propertyName));
			property = dataSetProperties.get(propertyName);
		}
		return (DataSetValueModel<T>) property;
	}

    @SuppressWarnings("unchecked")
    @Override
    public <T> GroupedDataSetValueModel<T> getGroupedDataSetProperty(String propertyName) {
        if(!groupedDataSetProperties.containsKey(propertyName)) {
            throw new RuntimeException("No such grouped dataSet property '"+describeProperty(propertyName)+"'");
        }

        GroupedDataSetPropertyValueModel property = groupedDataSetProperties.get(propertyName);
        if (property == null) {
            groupedDataSetProperties.put(propertyName, supply.createGroupedDataSetProperty(propertyName));
            property = groupedDataSetProperties.get(propertyName);
        }
        return (GroupedDataSetValueModel<T>) property;
    }
}
