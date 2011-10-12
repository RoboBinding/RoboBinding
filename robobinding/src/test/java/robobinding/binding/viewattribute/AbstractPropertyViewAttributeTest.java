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

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import robobinding.binding.BindingType;
import robobinding.binding.viewattribute.AbstractPropertyViewAttribute.PropertyBindingDetails;
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
	
	@Theory
	public void shouldDeterminePropertyBindingDetails(LegalPropertyViewAttributeValues attributeValues)
	{
		AbstractPropertyViewAttribute<?> propertyViewAttribute = new DummyPropertyViewAttribute();
		PropertyBindingDetails propertyBindingDetails = propertyViewAttribute.determinePropertyBindingDetails(attributeValues.value);
		
		assertThat(propertyBindingDetails.propertyName, equalTo(attributeValues.expectedPropertyName));
		assertThat(propertyBindingDetails.bindingType, equalTo(attributeValues.expectedBindingType));
	}
	
	@Theory
	@Test (expected=RuntimeException.class)
	public void whenDeterminingPropertyBindingDetailsForIllegalAttributeValues_ThenThrowARuntimeException(String illegalAttributeValue)
	{
		AbstractPropertyViewAttribute<?> propertyViewAttribute = new DummyPropertyViewAttribute();
		propertyViewAttribute.determinePropertyBindingDetails(illegalAttributeValue);
	}
	
	public static class LegalPropertyViewAttributeValues
	{
		public String value;
		public String expectedPropertyName;
		public BindingType expectedBindingType;
		public LegalPropertyViewAttributeValues(String value, String expectedPropertyName, BindingType expectedBindingType)
		{
			this.value = value;
			this.expectedPropertyName = expectedPropertyName;
			this.expectedBindingType = expectedBindingType;
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static class DummyPropertyViewAttribute extends AbstractPropertyViewAttribute
	{
		@Override
		protected void bindOnto(ValueModel valueModel, BindingType bindingType)
		{
		}
	}
}
