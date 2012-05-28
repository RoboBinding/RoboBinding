package org.robobinding.viewattribute;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;

public abstract class PropertyViewAttributeContractTest<T extends PropertyViewAttribute<?>>
{
	static final String ATTRIBUTE_NAME = "attributeName";
	static final String PROPERTY_NAME = "propertyName";
	T attribute;
	
	@Before
	public final void initializeAttribute()
	{
		attribute = createAttribute();
		ValueModelAttribute valueModelAttribute = mock(ValueModelAttribute.class);
		when(valueModelAttribute.getName()).thenReturn(ATTRIBUTE_NAME);
		when(valueModelAttribute.getPropertyName()).thenReturn(PROPERTY_NAME);
		attribute.setAttribute(valueModelAttribute);
	}
	
	protected T createAttribute()
	{
		ParameterizedType attributeType = (ParameterizedType)getClass().getGenericSuperclass();
		T attribute = ParameterizedTypeUtils.createTypeArgument(attributeType, 0);
		return attribute;
	}
	
	@Test (expected = AttributeBindingException.class)
	public void whenAnExceptionIsThrownDuringBinding_thenCatchAndRethrow()
	{
		BindingContext bindingContext = throwsExceptionDuringBinding();
		
		attribute.bindTo(bindingContext);
	}

	private BindingContext throwsExceptionDuringBinding()
	{
		BindingContext bindingContext = mock(BindingContext.class);
		PresentationModelAdapter presentationModelAdapter = mock(PresentationModelAdapter.class);
		when(bindingContext.getPresentationModelAdapter()).thenReturn(presentationModelAdapter);
		doThrow(new RuntimeException()).when(presentationModelAdapter).getPropertyType(anyString());
		return bindingContext;
	}
}
