package org.robobinding.attribute;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.attribute.StaticResourceTest.LegalStaticResource;
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
	public static LegalStaticResource[] legalStaticResourceAttributeValues = StaticResourceTest.legalStaticResources;

	@DataPoints
	public static IllegalValueModelAttributeValue[] illegalValueModelAttributeValues = {
			new IllegalValueModelAttributeValue("{propertyX", ErrorMessage.SIMPLE_ERROR_MESSAGE),
			new IllegalValueModelAttributeValue("propertyX", ErrorMessage.SUGGEST_CURLY_BRACES_ERROR_MESSAGE),
			new IllegalValueModelAttributeValue("propertyX}", ErrorMessage.SIMPLE_ERROR_MESSAGE),
			new IllegalValueModelAttributeValue("$${propertyX}", ErrorMessage.SIMPLE_ERROR_MESSAGE),
			new IllegalValueModelAttributeValue("!{propertyX}", ErrorMessage.SIMPLE_ERROR_MESSAGE) };

	@DataPoints
	public static IllegalStaticResourceAttributeValue[] illegalStaticResourceAttributeValues = { new IllegalStaticResourceAttributeValue("{@layout/layoutX}",
			ErrorMessage.SUGGEST_NOT_USING_CURLY_BRACES_FOR_STATIC_RESOURCES) };

	private PropertyAttributeParser parser;

	@Before
	public void setUp() {
		parser = new PropertyAttributeParser();
	}

	@Theory
	public void whenParseLegalValueModelAttributeValue_thenReturnValueModelAttribute(LegalValueModelAttributeValue legalValueModelAttributeValue) {
		AbstractPropertyAttribute attribute = parse(legalValueModelAttributeValue.value);

		assertThat(attribute, instanceOf(ValueModelAttribute.class));
	}

	@Theory
	public void whenParseLegalStaticResourceAttributeValue_thenReturnStaticResourceAttribute(LegalStaticResource legalStaticResourceAttributeValue) {
		AbstractPropertyAttribute attribute = parse(legalStaticResourceAttributeValue.value);

		assertThat(attribute, instanceOf(StaticResourceAttribute.class));
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
	public void givenLegalValueModelAttributeValue_whenParseAsValueModelAttribute_thenSuccessful(LegalValueModelAttributeValue legalValueModelAttributeValue) {
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
			LegalStaticResource legalStaticResourceAttributeValue) {
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

	private abstract static class AbstractIllegalAttributeValue {
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
				return "Invalid binding syntax: '" + value
						+ "'.\n\nDid you mean to omit the curly braces? (Curly braces are for binding to a property on the presentation model,"
						+ " not static resources)";
			default:
				throw new RuntimeException("should not reach here");
			}
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
