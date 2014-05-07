package org.robobinding.viewattribute.adapterview;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robobinding.attribute.Attributes.aValueModelAttribute;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.property.DataSetValueModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SourceAttributeTest {
    private final String attributeValue = "{property_name}";
    private final String propertyName = "property_name";
    @Mock
    private DataSetAdapterBuilder dataSetAdapterBuilder;
    @Mock
    private BindingContext bindingContext;
    @Mock
    private DataSetValueModel dataSetValueModel;

    @Test
    public void whenBinding_thenSetDataSetValueModelOnDataSetAdapterBuilder() {
	when(bindingContext.getDataSetPropertyValueModel(propertyName)).thenReturn(dataSetValueModel);
	SourceAttribute sourceAttribute = new SourceAttribute(dataSetAdapterBuilder);
	sourceAttribute.setAttribute(aValueModelAttribute(attributeValue));

	sourceAttribute.bindTo(bindingContext);

	verify(dataSetAdapterBuilder).setValueModel(dataSetValueModel);
    }
}
