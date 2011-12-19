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

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

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
	public static IllegalBindingAttributeValue[] illegalAttributeValues = {
		new IllegalBindingAttributeValue("{propertyX", ErrorMessage.SIMPLE_ERROR_MESSAGE),
		new IllegalBindingAttributeValue("propertyX", ErrorMessage.SUGGEST_CURLY_BRACES_ERROR_MESSAGE),
		new IllegalBindingAttributeValue("propertyX}", ErrorMessage.SIMPLE_ERROR_MESSAGE),
		new IllegalBindingAttributeValue("$${propertyX}", ErrorMessage.SIMPLE_ERROR_MESSAGE),
		new IllegalBindingAttributeValue("!{propertyX}", ErrorMessage.SIMPLE_ERROR_MESSAGE),
		new IllegalBindingAttributeValue("{@layout/layoutX}", ErrorMessage.SUGGEST_NOT_USING_CURLY_BRACES_FOR_STATIC_RESOURCES)
	};
	
	public enum ErrorMessage {SIMPLE_ERROR_MESSAGE, SUGGEST_CURLY_BRACES_ERROR_MESSAGE, SUGGEST_NOT_USING_CURLY_BRACES_FOR_STATIC_RESOURCES}
	
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
	public void whenBindingWithIllegalAttributeValues_ThenThrowARuntimeException(IllegalBindingAttributeValue illegalBindingAttributeValue)
	{
		boolean exceptionThrown = false;
		
		try
		{
			new BindingDetailsBuilder(illegalBindingAttributeValue.value, false);
		} catch (RuntimeException e)
		{
			assertThat(e.getMessage(), equalTo(illegalBindingAttributeValue.getExpectedErrorMessage()));
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
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
	
	static class IllegalBindingAttributeValue
	{
		final String value;
		final ErrorMessage expectedErrorMessage;
		
		public IllegalBindingAttributeValue(String value, ErrorMessage expectedErrorMessage)
		{
			this.value = value;
			this.expectedErrorMessage = expectedErrorMessage;
		}

		public String getExpectedErrorMessage()
		{
			switch (expectedErrorMessage)
			{
			case SIMPLE_ERROR_MESSAGE:
				return "Invalid binding syntax: '" + value + "'.";
			case SUGGEST_CURLY_BRACES_ERROR_MESSAGE:
				return "Invalid binding syntax: '" + value + "'.\n\nDid you mean '{" + value + "}' or '${" + value + "}'? (one/two-way binding)\n";
			case SUGGEST_NOT_USING_CURLY_BRACES_FOR_STATIC_RESOURCES:
				return "Invalid binding syntax: '" + value + "'.\n\nDid you mean to omit the curly braces? (Curly braces are for binding to a property on the presentation model, not static resources)";
			}
			
			return "";
		}
	}
}
