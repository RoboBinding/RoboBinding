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
import org.robobinding.attribute.StaticResourceAttributeTest.LegalStaticResourceAttributeValue;
import org.robobinding.attribute.ValueModelAttributeTest.LegalValueModelAttributeValue;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(Theories.class)
public class PropertyAttributeParserTest {
    @DataPoints
    public static LegalValueModelAttributeValue[] legalValueModelAttributeValues = ValueModelAttributeTest.legalAttributeValues;

    @DataPoints
    public static LegalStaticResourceAttributeValue[] legalStaticResourceAttributeValues = StaticResourceAttributeTest.legalAttributeValues;

    @DataPoints
    public static IllegalValueModelAttributeValue[] illegalValueModelAttributeValues = {
	    new IllegalValueModelAttributeValue("{propertyX", ErrorMessage.SIMPLE_ERROR_MESSAGE),
	    new IllegalValueModelAttributeValue("propertyX", ErrorMessage.SUGGEST_CURLY_BRACES_ERROR_MESSAGE),
	    new IllegalValueModelAttributeValue("propertyX}", ErrorMessage.SIMPLE_ERROR_MESSAGE),
	    new IllegalValueModelAttributeValue("$${propertyX}", ErrorMessage.SIMPLE_ERROR_MESSAGE),
	    new IllegalValueModelAttributeValue("!{propertyX}", ErrorMessage.SIMPLE_ERROR_MESSAGE) };

    @DataPoints
    public static IllegalStaticResourceAttributeValue[] illegalStaticResourceAttributeValues = { new IllegalStaticResourceAttributeValue(
	    "{@layout/layoutX}", ErrorMessage.SUGGEST_NOT_USING_CURLY_BRACES_FOR_STATIC_RESOURCES) };

    private PropertyAttributeParser parser;

    @Before
    public void setUp() {
	parser = new PropertyAttributeParser();
    }

    @Theory
    public void whenParseLegalValueModelAttributeValue_thenReturnValueModelAttribute(LegalValueModelAttributeValue legalValueModelAttributeValue) {
	AbstractPropertyAttribute attribute = parse(legalValueModelAttributeValue.value);

	assertTrue(attribute.isValueModel());
    }

    @Theory
    public void whenParseLegalStaticResourceAttributeValue_thenReturnStaticResourceAttribute(
	    LegalStaticResourceAttributeValue legalStaticResourceAttributeValue) {
	AbstractPropertyAttribute attribute = parse(legalStaticResourceAttributeValue.value);

	assertTrue(attribute.isStaticResource());
    }

    @Theory
    public void whenParseIllegalAttributeValue_thenThrowException(AbstractIllegalAttributeValue illegalAttributeValue) {
	try {
	    parse(illegalAttributeValue.value);
	    fail("Expect an exception thrown");
	} catch (MalformedAttributeException e) {
	    assertThat(e.getMessage(), equalTo(illegalAttributeValue.getExpectedErrorMessage()));
	}
    }

    private AbstractPropertyAttribute parse(String attributeValue) {
	return parser.parse("name", attributeValue);
    }

    @Theory
    public void givenLegalValueModelAttributeValue_whenParseAsValueModelAttribute_thenSuccessful(
	    LegalValueModelAttributeValue legalValueModelAttributeValue) {
	parseAsValueModelAttribute(legalValueModelAttributeValue.value);
    }

    @Theory
    public void givenIllegalValueModelAttributeValue_whenParseAsValueModelAttribute_thenThrowException(
	    IllegalValueModelAttributeValue illegalValueModelAttributeValue) {
	try {
	    parseAsValueModelAttribute(illegalValueModelAttributeValue.value);
	    fail("Expect an exception thrown");
	} catch (MalformedAttributeException e) {
	    assertThat(e.getMessage(), equalTo(illegalValueModelAttributeValue.getExpectedErrorMessage()));
	}
    }

    private ValueModelAttribute parseAsValueModelAttribute(String attributeValue) {
	return parser.parseAsValueModelAttribute("name", attributeValue);
    }

    @Theory
    public void givenLegalStaticResourceAttributeValue_whenParseAsValueModelAttribute_thenSuccessful(
	    LegalStaticResourceAttributeValue legalStaticResourceAttributeValue) {
	parseAsStaticResourceAttribute(legalStaticResourceAttributeValue.value);
    }

    @Theory
    public void givenIllegalStaticResourceAttributeValue_whenParseAsStaticResourceAttribute_thenThrowException(
	    IllegalStaticResourceAttributeValue illegalStaticResourceAttributeValue) {
	try {
	    parseAsStaticResourceAttribute(illegalStaticResourceAttributeValue.value);
	    fail("Expect an exception thrown");
	} catch (MalformedAttributeException e) {
	    assertThat(e.getMessage(), equalTo(illegalStaticResourceAttributeValue.getExpectedErrorMessage()));
	}
    }

    private StaticResourceAttribute parseAsStaticResourceAttribute(String attributeValue) {
	return parser.parseAsStaticResourceAttribute("name", attributeValue);
    }

    public enum ErrorMessage {
	SIMPLE_ERROR_MESSAGE, SUGGEST_CURLY_BRACES_ERROR_MESSAGE, SUGGEST_NOT_USING_CURLY_BRACES_FOR_STATIC_RESOURCES
    }

    private static abstract class AbstractIllegalAttributeValue {
	final String value;
	final ErrorMessage expectedErrorMessage;

	public AbstractIllegalAttributeValue(String value, ErrorMessage expectedErrorMessage) {
	    this.value = value;
	    this.expectedErrorMessage = expectedErrorMessage;
	}

	public String getExpectedErrorMessage() {
	    switch (expectedErrorMessage) {
	    case SIMPLE_ERROR_MESSAGE:
		return "Invalid binding syntax: '" + value + "'.";
	    case SUGGEST_CURLY_BRACES_ERROR_MESSAGE:
		return "Invalid binding syntax: '" + value + "'.\n\nDid you mean '{" + value + "}' or '${" + value + "}'? (one/two-way binding)\n";
	    case SUGGEST_NOT_USING_CURLY_BRACES_FOR_STATIC_RESOURCES:
		return "Invalid binding syntax: '"
			+ value
			+ "'.\n\nDid you mean to omit the curly braces? (Curly braces are for binding to a property on the presentation model, not static resources)";
	    }

	    return "";
	}
    }

    private static class IllegalValueModelAttributeValue extends AbstractIllegalAttributeValue {
	public IllegalValueModelAttributeValue(String value, ErrorMessage expectedErrorMessage) {
	    super(value, expectedErrorMessage);
	}
    }

    private static class IllegalStaticResourceAttributeValue extends AbstractIllegalAttributeValue {
	public IllegalStaticResourceAttributeValue(String value, ErrorMessage expectedErrorMessage) {
	    super(value, expectedErrorMessage);
	}
    }

}
