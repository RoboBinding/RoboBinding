package org.robobinding.widget.adapterview;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robobinding.attribute.Attributes.aValueModelAttribute;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.ItemBindingContext;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
public class SourceAttributeTest {
	private final String attributeValue = "{property_name}";
	private final String propertyName = "property_name";
	@Mock
	private DataSetAdapterBuilder dataSetAdapterBuilder;
	@Mock
	private BindingContext bindingContext;
	@Mock
	private ItemBindingContext itemBindingContext;

	@Test
	public void whenBinding_thenSetDataSetValueModelOnDataSetAdapterBuilder() {
		when(bindingContext.navigateToItemContext(propertyName)).thenReturn(itemBindingContext);
		SourceAttribute sourceAttribute = new SourceAttribute(dataSetAdapterBuilder);
		sourceAttribute.setAttribute(aValueModelAttribute(attributeValue));

		sourceAttribute.bindTo(bindingContext);

		verify(dataSetAdapterBuilder).setBindingContext(itemBindingContext);
	}
}
