package org.robobinding.viewattribute;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.attribute.AbstractAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ChildViewAttributeInitializerTest {
    @Mock
    private AbstractAttribute attribute;

    @Test
    public void whenInitializeChildViewAttribute_thenNoThingHappens() {
	ChildViewAttribute childViewAttribute = mock(ChildViewAttribute.class);

	ChildViewAttributeInitializer viewAttributeInitializer = new ChildViewAttributeInitializer(null);
	viewAttributeInitializer.initializeChildViewAttribute(childViewAttribute, attribute);
    }

    @Test
    public void whenInitializeChildViewAttributeWithAttribute_thenTheAttributeIsSetOnTheChildViewAttribute() {
	@SuppressWarnings("unchecked")
	ChildViewAttributeWithAttribute<AbstractAttribute> childViewAttribute = mock(ChildViewAttributeWithAttribute.class);

	ChildViewAttributeInitializer viewAttributeInitializer = new ChildViewAttributeInitializer(null);
	viewAttributeInitializer.initializeChildViewAttribute(childViewAttribute, attribute);

	verify(childViewAttribute).setAttribute(attribute);
    }
}
