package org.robobinding.attribute;

import static org.mockito.Mockito.when;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockValueModelAttributeBuilder extends AbstractMockAttributeBuilder<ValueModelAttribute> {
    private MockValueModelAttributeBuilder() {
	super(ValueModelAttribute.class);
    }

    private void declarePropertyName(String propertyName) {
	when(attribute.getPropertyName()).thenReturn(propertyName);
    }

    private void declareTwoWayBinding(boolean twoWayBinding) {
	when(attribute.isTwoWayBinding()).thenReturn(twoWayBinding);
    }

    public static ValueModelAttribute aValueModelAttribute(String propertyName, boolean twoWayBinding) {
	MockValueModelAttributeBuilder builder = new MockValueModelAttributeBuilder();
	builder.declarePropertyName(propertyName);
	builder.declareTwoWayBinding(twoWayBinding);
	return builder.attribute;
    }

    public static ValueModelAttribute aValueModelAttribute() {
	MockValueModelAttributeBuilder builder = new MockValueModelAttributeBuilder();
	return builder.attribute;
    }
}
