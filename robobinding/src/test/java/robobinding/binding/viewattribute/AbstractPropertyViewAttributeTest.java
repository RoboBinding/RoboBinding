/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package robobinding.binding.viewattribute;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import robobinding.beans.PresentationModelAdapter;
import robobinding.binding.widgetattribute.AbstractPropertyWidgetAttribute;
import robobinding.value.ValueModel;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@RunWith(Theories.class)
public class AbstractPropertyViewAttributeTest
{
	private enum BindingType {NO_BINDING, ONE_WAY, TWO_WAY}
	
	@DataPoints
	public static LegalPropertyViewAttributeValues[] legalPropertyViewAttributeValues = {
		new LegalPropertyViewAttributeValues("{propertyX}", "propertyX", BindingType.ONE_WAY),
		new LegalPropertyViewAttributeValues("{propertyY}", "propertyY", BindingType.ONE_WAY),
		new LegalPropertyViewAttributeValues("${propertyZ}", "propertyZ", BindingType.TWO_WAY)
	};
	
	@DataPoints
	public static String[] illegalAttributeValues = {
		"{propertyX", "propertyX", "propertyX}", "$${propertyX}", "!{propertyX}"
	};
	
	private PresentationModelAdapter presentationModelAdapter;
	
	@Before
	public void setUp()
	{
		presentationModelAdapter = mock(PresentationModelAdapter.class);
	}
	
	@SuppressWarnings("unchecked")
	@Theory
	public void whenBindingWithLegalAttributeValues_thenBindCorrectly(LegalPropertyViewAttributeValues attributeValues)
	{
		DummyPropertyViewAttribute propertyViewAttribute = new DummyPropertyViewAttribute();
		
		ValueModel<Object> valueModel = mock(ValueModel.class);
		when(presentationModelAdapter.getReadOnlyPropertyValueModel(attributeValues.expectedPropertyName)).thenReturn(valueModel);
		when(presentationModelAdapter.getPropertyValueModel(attributeValues.expectedPropertyName)).thenReturn(valueModel);
		
		propertyViewAttribute.bind(presentationModelAdapter, attributeValues.value);
				
		assertThat(propertyViewAttribute.valueModelBound, equalTo(valueModel));
		assertThat(propertyViewAttribute.bindingType, equalTo(attributeValues.expectedBindingType));
	}
	
	@Theory
	@Test (expected=RuntimeException.class)
	public void whenBindingWithIllegalAttributeValues_ThenThrowARuntimeException(String illegalAttributeValue)
	{
		AbstractPropertyWidgetAttribute<?> propertyViewAttribute = new DummyPropertyViewAttribute();
		propertyViewAttribute.bind(presentationModelAdapter, illegalAttributeValue);
	}
	
	static class LegalPropertyViewAttributeValues
	{
		String value;
		String expectedPropertyName;
		BindingType expectedBindingType;
		public LegalPropertyViewAttributeValues(String value, String expectedPropertyName, BindingType expectedBindingType)
		{
			this.value = value;
			this.expectedPropertyName = expectedPropertyName;
			this.expectedBindingType = expectedBindingType;
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static class DummyPropertyViewAttribute extends AbstractPropertyWidgetAttribute
	{
		private BindingType bindingType = BindingType.NO_BINDING;
		private ValueModel valueModelBound;
		
		@Override
		protected void initializeView(ValueModel valueModel)
		{
		}

		@Override
		protected void observeChangesOnTheValueModel(ValueModel valueModel)
		{
			if (bindingType != BindingType.TWO_WAY)
				bindingType = BindingType.ONE_WAY;
		
			valueModelBound = valueModel;
		}

		@Override
		protected void observeChangesOnTheView(ValueModel valueModel)
		{
			bindingType = BindingType.TWO_WAY;
		}
	}
}
