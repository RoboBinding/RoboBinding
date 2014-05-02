package org.robobinding.attribute;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robobinding.attribute.Attributes.aCommandAttribute;
import static org.robobinding.attribute.ResolvedGroupAttributesBuilder.aGroupAttributes;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class ResolvedGroupAttributesTest {
    @DataPoints
    public static ChildAttributeExpectation[] childAttributeExpectations = {aChildAttributeExpectation("commandAttribute", CommandAttribute.class),
	    aChildAttributeExpectation("valueModelAttribute", ValueModelAttribute.class),
	    aChildAttributeExpectation("staticResourceAttribute", StaticResourceAttribute.class),
	    aChildAttributeExpectation("predefinedMappingsAttribute", PredefinedMappingsAttribute.class),
	    aChildAttributeExpectation("enumAttribute", EnumAttribute.class)};

    @Theory
    public void givenChildAttribute_whenAskForAttribute_thenReturnAttributeOfCorrectType(ChildAttributeExpectation childAttributeExpectation) {
	String attributeName = childAttributeExpectation.attributeName();
	ResolvedGroupAttributes groupedAttribute = aGroupAttributes().withChildAttributeResolution(childAttributeExpectation.attribute).build();

	AbstractAttribute attribute = groupedAttribute.attributeFor(attributeName);

	assertThat(attribute, instanceOf(childAttributeExpectation.expectedType()));
    }

    @Test(expected = ClassCastException.class)
    public void givenChildAttribute_whenAskForAttributeOfIncorrectType_thenThrowException() {
	CommandAttribute commandAttribute = aCommandAttribute("commandName");
	ResolvedGroupAttributes groupedAttribute = aGroupAttributes().withChildAttributeResolution(commandAttribute).build();

	groupedAttribute.valueModelAttributeFor(commandAttribute.getName());
    }

    private static ChildAttributeExpectation aChildAttributeExpectation(String attributeName, Class<? extends AbstractAttribute> attributeType) {
	return new ChildAttributeExpectation(attributeName, attributeType);
    }

    private static class ChildAttributeExpectation {
	private final AbstractAttribute attribute;

	public ChildAttributeExpectation(String attributeName, Class<? extends AbstractAttribute> attributeType) {
	    attribute = mock(attributeType);
	    when(attribute.getName()).thenReturn(attributeName);
	}

	public String attributeName() {
	    return attribute.getName();
	}

	public Class<?> expectedType() {
	    return attribute.getClass();
	}
    }

}
