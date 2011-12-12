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
package org.robobinding.binding.viewattribute;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.robobinding.binding.viewattribute.BindingType.ONE_WAY;
import static org.robobinding.binding.viewattribute.BindingType.TWO_WAY;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.binding.viewattribute.BindingDetailsBuilder;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(Theories.class)
public class BindingDetailsBuilderTest
{
	@DataPoints
	public static LegalPropertyAttributeValues[] legalPropertyAttributeValues = {
		new LegalPropertyAttributeValues("{propertyX}", "propertyX", ONE_WAY),
		new LegalPropertyAttributeValues("{propertyY}", "propertyY", ONE_WAY),
		new LegalPropertyAttributeValues("${propertyZ}", "propertyZ", TWO_WAY)
	};
	
	@DataPoints
	public static LegalResourceAttributeValues[] legalResourceAttributeValues = {
		new LegalResourceAttributeValues("@layout/layoutX", "layoutX", "layout", null),
		new LegalResourceAttributeValues("@android:layout/layoutY", "layoutY", "layout", "android"),
		new LegalResourceAttributeValues("@com.somecompany.widget:layout/layoutY", "layoutY", "layout", "com.somecompany.widget")
	};
	
	@DataPoints
	public static String[] illegalAttributeValues = {
		"{propertyX", "propertyX", "propertyX}", "$${propertyX}", "!{propertyX}"
	};
	
	@Theory
	public void givenLegalPropertyAttributeValues(LegalPropertyAttributeValues legalAttributeValues)
	{
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(legalAttributeValues.value, false);
		
		assertFalse(bindingDetailsBuilder.bindsToStaticResource());
		assertThat(bindingDetailsBuilder.getPropertyName(), equalTo(legalAttributeValues.expectedPropertyName));
		assertThat(bindingDetailsBuilder.isTwoWayBinding(), equalTo(legalAttributeValues.expectedBindingType == TWO_WAY ? true : false));
	}
	
	@Theory
	public void givenLegalResourceAttributeValues(LegalResourceAttributeValues legalAttributeValues)
	{
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(legalAttributeValues.value, false);

		assertTrue(bindingDetailsBuilder.bindsToStaticResource());
		assertThat(bindingDetailsBuilder.getResourceName(), equalTo(legalAttributeValues.expectedName));
		assertThat(bindingDetailsBuilder.getResourceType(), equalTo(legalAttributeValues.expectedType));
		assertThat(bindingDetailsBuilder.getResourcePackage(), equalTo(legalAttributeValues.expectedPackage));
	}
	
	@Theory
	@Test (expected=RuntimeException.class)
	public void whenBindingWithIllegalAttributeValues_ThenThrowARuntimeException(String illegalAttributeValue)
	{
		new BindingDetailsBuilder(illegalAttributeValue, false);
	}
	
	static class LegalPropertyAttributeValues
	{
		final String value;
		final String expectedPropertyName;
		final BindingType expectedBindingType;
		
		public LegalPropertyAttributeValues(String value, String expectedPropertyName, BindingType expectedBindingType)
		{
			this.value = value;
			this.expectedPropertyName = expectedPropertyName;
			this.expectedBindingType = expectedBindingType;
		}
	}
	
	static class LegalResourceAttributeValues
	{
		private final String value;
		private final String expectedName;
		private final String expectedType;
		private final String expectedPackage;

		public LegalResourceAttributeValues(String value, String expectedName, String expectedType, String expectedPackage)
		{
			this.value = value;
			this.expectedName = expectedName;
			this.expectedType = expectedType;
			this.expectedPackage = expectedPackage;
		}

	}
}
