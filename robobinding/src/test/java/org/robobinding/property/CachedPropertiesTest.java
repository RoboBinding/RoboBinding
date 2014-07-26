package org.robobinding.property;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CachedPropertiesTest {
    private static final String PROPERTY_NAME = "property";

    @Test
    public void givenGetReadOnlyPropertyOnce_whenGetReadOnlyPropertyAgain_thenReturnSameInstance() {
	PropertiesWithDependency properties = mock(PropertiesWithDependency.class);
	when(properties.createProperty(PROPERTY_NAME)).thenReturn(mock(PropertyValueModel.class));
	CachedProperties cachedProperties = new CachedProperties(properties);

	ValueModel<Object> property = cachedProperties.getReadOnlyProperty(PROPERTY_NAME);

	ValueModel<Object> cachedProperty = cachedProperties.getReadOnlyProperty(PROPERTY_NAME);

	assertThat(cachedProperty, sameInstance(property));
    }

    @Test
    public void givenAccessReadWritePropertyUsingGetReadOnlyPropertyAtFirst_whenAccessUsingGetReadWritePropertyLater_thenReturnSameInstance() {
	PropertiesWithDependency properties = mock(PropertiesWithDependency.class);
	when(properties.createProperty(PROPERTY_NAME)).thenReturn(mock(PropertyValueModel.class));
	CachedProperties cachedProperties = new CachedProperties(properties);

	ValueModel<Boolean> propertyUsingGetReadOnlyProperty = cachedProperties.getReadOnlyProperty(PROPERTY_NAME);

	ValueModel<Boolean> propertyUsingGetReadWriteProperty = cachedProperties.getReadWriteProperty(PROPERTY_NAME);

	assertThat(propertyUsingGetReadWriteProperty, sameInstance(propertyUsingGetReadOnlyProperty));
    }

    @Test
    public void givenGetDatSetPropertyOnce_whenGetDataSetPropertyAgain_thenReturnSameInstance() {
	PropertiesWithDependency properties = mock(PropertiesWithDependency.class);
	when(properties.createDataSetProperty(PROPERTY_NAME)).thenReturn(mock(DataSetPropertyValueModel.class));
	CachedProperties cachedProperties = new CachedProperties(properties);

	DataSetValueModel<List<Boolean>> dataSetProperty = cachedProperties.getDataSetProperty(PROPERTY_NAME);

	DataSetValueModel<List<Boolean>> cachedDataSetProperty = cachedProperties.getDataSetProperty(PROPERTY_NAME);

	assertThat(cachedDataSetProperty, sameInstance(dataSetProperty));
    }
}
