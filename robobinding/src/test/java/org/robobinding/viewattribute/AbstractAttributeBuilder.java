package org.robobinding.viewattribute;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.robobinding.attribute.AbstractAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class AbstractAttributeBuilder<T extends AbstractAttribute> {
    protected T attribute;

    protected AbstractAttributeBuilder(Class<T> classToMock) {
	attribute = mock(classToMock);
    }

    protected void declareAttributeName(String name) {
	when(attribute.getName()).thenReturn(name);
    }
}
