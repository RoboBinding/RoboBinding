/**
 * CachedPropertiesTest.java
 * Oct 29, 2011 Copyright Cheng Wei and Robert Taylor
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
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
