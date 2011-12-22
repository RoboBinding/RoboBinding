package org.robobinding.viewattribute;

import org.robobinding.function.Function;
import org.robobinding.internal.org_apache_commons_lang3.Validate;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.DataSetProperty;
import org.robobinding.property.PropertyValueModel;
import org.robobinding.property.ValueModelUtils;

import android.app.Activity;

public class MockPresentationModelAdapterForProperty<T> implements PresentationModelAdapter
{
	private static final String PROPERTY_NAME = "propertyName";
	public static final String ONE_WAY_BINDING_PROPERTY_NAME = "{"+PROPERTY_NAME+"}";
	public static final String TWO_WAY_BINDING_PROPERTY_NAME = "$" + ONE_WAY_BINDING_PROPERTY_NAME;
	
	private Class<?> propertyType;
	private PropertyValueModel<T> propertyValueModel;
	
	@SuppressWarnings("unchecked")
	private MockPresentationModelAdapterForProperty(Class<T> propertyType)
	{
		this.propertyType = propertyType;
		
		if (PrimitiveTypeUtils.floatIsAssignableFrom(propertyType))
			propertyValueModel = (PropertyValueModel<T>)ValueModelUtils.createFloat(0f);
		else if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType))
			propertyValueModel = (PropertyValueModel<T>)ValueModelUtils.createInteger(0);
		else if (PrimitiveTypeUtils.booleanIsAssignableFrom(propertyType))
			propertyValueModel = (PropertyValueModel<T>)ValueModelUtils.createBoolean(false);
		else
			propertyValueModel = ValueModelUtils.create();
	}
	
	public void updatePropertyValue(T newValue)
	{
		propertyValueModel.setValue(newValue);
	}
	
	public T getPropertyValue()
	{
		return propertyValueModel.getValue();
	}
	
	@Override
	public Class<?> getPropertyType(String propertyName)
	{
		checkPropertyName(propertyName);
		return propertyType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PropertyValueModel<T> getReadOnlyPropertyValueModel(String propertyName)
	{
		checkPropertyName(propertyName);
		return propertyValueModel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PropertyValueModel<T> getPropertyValueModel(String propertyName)
	{
		checkPropertyName(propertyName);
		return propertyValueModel;
	}

	private void checkPropertyName(String propertyName)
	{
		Validate.isTrue(PROPERTY_NAME.equals(propertyName), "invalid property name");
	}

	@Override
	public DataSetProperty<T> getDataSetPropertyValueModel(String propertyName)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Function findFunction(String functionName, Class<?>... parameterTypes)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<?> getPresentationModelClass()
	{
		throw new UnsupportedOperationException();
	}
	
	
	public static <T> MockPresentationModelAdapterForProperty<T> bindToProperty(ViewAttribute viewAttribute, Class<T> propertyType)
	{
		MockPresentationModelAdapterForProperty<T> mockPresentationModelAdapter = new MockPresentationModelAdapterForProperty<T>(propertyType);
		viewAttribute.bind(mockPresentationModelAdapter, new Activity());
		return mockPresentationModelAdapter;
	}
}