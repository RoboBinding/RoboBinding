package org.robobinding.property;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CachedPropertiesTest {
    private static final String PROPERTY_NAME = "property";
    private CachedProperties cachedProperties;

    @Test
    public void givenGetReadOnlyPropertyOnce_whenGetReadOnlyPropertyAgain_thenReturnSameInstance() {
	declareReadOnlyProperty();

	ValueModel<Boolean> property = cachedProperties.getReadOnlyProperty(PROPERTY_NAME);

	ValueModel<Boolean> cachedProperty = cachedProperties.getReadOnlyProperty(PROPERTY_NAME);

	Assert.assertTrue(property == cachedProperty);
    }

    private void declareReadOnlyProperty() {
	PropertyCreator mockPropertyCreator = MockPropertyCreatorBuilder.createWithReadOnlyProperty(PROPERTY_NAME);
	cachedProperties = new CachedProperties(mockPropertyCreator);
    }

    @Test
    public void givenAccessReadWritePropertyUsingGetReadOnlyPropertyAtFirst_whenAccessUsingGetReadWritePropertyLater_thenReturnSameInstance() {
	declareProperty();

	ValueModel<Boolean> propertyUsingGetReadOnlyProperty = cachedProperties.getReadOnlyProperty(PROPERTY_NAME);

	ValueModel<Boolean> propertyUsingGetReadWriteProperty = cachedProperties.getReadWriteProperty(PROPERTY_NAME);

	Assert.assertTrue(propertyUsingGetReadOnlyProperty == propertyUsingGetReadWriteProperty);
    }

    private void declareProperty() {
	PropertyCreator mockPropertyCreator = MockPropertyCreatorBuilder.createWithProperty(PROPERTY_NAME);
	cachedProperties = new CachedProperties(mockPropertyCreator);
    }

    @Test
    public void givenGetDatSetPropertyOnce_whenGetDataSetPropertyAgain_thenReturnSameInstance() {
	declareDataSetProperty();

	DataSetValueModel<List<Boolean>> dataSetProperty = cachedProperties.getDataSetProperty(PROPERTY_NAME);

	DataSetValueModel<List<Boolean>> cachedDataSetProperty = cachedProperties.getDataSetProperty(PROPERTY_NAME);

	Assert.assertTrue(dataSetProperty == cachedDataSetProperty);
    }

    private void declareDataSetProperty() {
	PropertyCreator mockPropertyCreator = MockPropertyCreatorBuilder.createWithDataSetProperty(PROPERTY_NAME);
	cachedProperties = new CachedProperties(mockPropertyCreator);
    }
}
