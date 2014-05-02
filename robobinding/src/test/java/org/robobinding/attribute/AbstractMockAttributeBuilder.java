package org.robobinding.attribute;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class AbstractMockAttributeBuilder<T extends AbstractAttribute> {
    protected T attribute;

    protected AbstractMockAttributeBuilder(Class<T> classToMock) {
	attribute = mock(classToMock);
    }

    protected void declareAttributeName(String name) {
	when(attribute.getName()).thenReturn(name);
    }
}
