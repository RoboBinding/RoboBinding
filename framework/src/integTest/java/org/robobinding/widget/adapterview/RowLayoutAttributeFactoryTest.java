package org.robobinding.widget.adapterview;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robobinding.attribute.Attributes.aValueModelAttribute;

import org.junit.Test;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributeAdapter;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class RowLayoutAttributeFactoryTest {
    @Test
    public void whenCreateWithStaticResourceAttribute_thenReturnStaticLayoutAttribute() {
	StaticResourceAttribute staticResourceAttribute = mock(StaticResourceAttribute.class);
	when(staticResourceAttribute.isStaticResource()).thenReturn(true);

	RowLayoutAttributeFactory rowLayoutAttributeFactory = new ItemLayoutAttributeFactory(null, null);
	ChildViewAttribute viewAttribute = rowLayoutAttributeFactory.createRowLayoutAttribute(staticResourceAttribute);

	assertThat(viewAttribute, instanceOf(StaticLayoutAttribute.class));
    }

    @Test
    public void whenCreateWithValueModelAttribute_thenReturnDynamicLayoutAttribute() {
	ValueModelAttribute valueModelAttribute = aValueModelAttribute("{itemLayout}");

	RowLayoutAttributeFactory rowLayoutAttributeFactory = new ItemLayoutAttributeFactory(null, null);
	ChildViewAttribute viewAttribute = rowLayoutAttributeFactory.createRowLayoutAttribute(valueModelAttribute);

	assertThat(viewAttribute, instanceOf(ChildViewAttributeAdapter.class));
    }
}
