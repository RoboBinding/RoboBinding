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
package org.robobinding.attribute;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.attribute.StaticResourceAttributeTest.LegalResourceAttributeValues;
import org.robobinding.attribute.ValueModelAttributeTest.LegalValueModelAttributeValues;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(Theories.class)
public class PropertyAttributeParserTest
{
	@DataPoints
	public static LegalValueModelAttributeValues[] legalValueModelAttributeValues = ValueModelAttributeTest.legalAttributeValues;
	
	@DataPoints
	public static LegalResourceAttributeValues[] legalResourceAttributeValues = StaticResourceAttributeTest.legalAttributeValues;
	
	@DataPoints
	public static IllegalBindingAttributeValue[] illegalAttributeValues = {
		new IllegalBindingAttributeValue("{propertyX", ErrorMessage.SIMPLE_ERROR_MESSAGE),
		new IllegalBindingAttributeValue("propertyX", ErrorMessage.SUGGEST_CURLY_BRACES_ERROR_MESSAGE),
		new IllegalBindingAttributeValue("propertyX}", ErrorMessage.SIMPLE_ERROR_MESSAGE),
		new IllegalBindingAttributeValue("$${propertyX}", ErrorMessage.SIMPLE_ERROR_MESSAGE),
		new IllegalBindingAttributeValue("!{propertyX}", ErrorMessage.SIMPLE_ERROR_MESSAGE),
		new IllegalBindingAttributeValue("{@layout/layoutX}", ErrorMessage.SUGGEST_NOT_USING_CURLY_BRACES_FOR_STATIC_RESOURCES)
	};
	
	private PropertyAttributeParser parser;
	
	@Before
	public void setUp()
	{
		parser = new PropertyAttributeParser();
	}
	
	@Theory
	public void givenLegalValueModelAttributeValues(LegalValueModelAttributeValues legalAttributeValues)
	{
		AbstractPropertyAttribute attribute = parse(legalAttributeValues.value);
		
		assertTrue(attribute.isValueModel());
	}
	
	@Theory
	public void givenLegalResourceAttributeValues(LegalResourceAttributeValues legalAttributeValues)
	{
		AbstractPropertyAttribute attribute = parse(legalAttributeValues.value);

		assertTrue(attribute.isStaticResource());
	}
	
	@Theory
	public void whenBindingWithIllegalAttributeValues_thenThrowARuntimeException(IllegalBindingAttributeValue illegalBindingAttributeValue)
	{
		try
		{
			parse(illegalBindingAttributeValue.value);
			fail("Expect an exception thrown");
		} catch (RuntimeException e)
		{
			assertThat(e.getMessage(), equalTo(illegalBindingAttributeValue.getExpectedErrorMessage()));
		}
	}

	private AbstractPropertyAttribute parse(String attributeValue)
	{
		return parser.parse("name", attributeValue);
	}
	
	
	public enum ErrorMessage {SIMPLE_ERROR_MESSAGE, SUGGEST_CURLY_BRACES_ERROR_MESSAGE, SUGGEST_NOT_USING_CURLY_BRACES_FOR_STATIC_RESOURCES}


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
