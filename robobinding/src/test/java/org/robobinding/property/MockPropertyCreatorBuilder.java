/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockPropertyCreatorBuilder {
    private PropertyCreator mockPropertyCreator;

    public MockPropertyCreatorBuilder() {
	mockPropertyCreator = mock(PropertyCreator.class);
    }

    public MockPropertyCreatorBuilder declareProperty(String propertyName) {
	@SuppressWarnings("unchecked")
	PropertyValueModel<Object> mockProperty = (PropertyValueModel<Object>) mock(PropertyValueModel.class);
	when(mockPropertyCreator.createProperty(propertyName)).thenReturn(mockProperty);

	return this;
    }

    public MockPropertyCreatorBuilder declareReadOnlyProperty(String propertyName) {
	@SuppressWarnings("unchecked")
	PropertyValueModel<Object> mockReadOnlyProperty = (PropertyValueModel<Object>) mock(PropertyValueModel.class);
	doThrow(new RuntimeException()).when(mockReadOnlyProperty).checkReadWriteProperty(true);
	when(mockPropertyCreator.createProperty(propertyName)).thenReturn(mockReadOnlyProperty);

	return this;
    }

    public MockPropertyCreatorBuilder declareDataSetProperty(String propertyName) {
	@SuppressWarnings("unchecked")
	DataSetPropertyValueModel<Object> mockDataSetProperty = (DataSetPropertyValueModel<Object>) mock(DataSetPropertyValueModel.class);
	when(mockPropertyCreator.createDataSetProperty(propertyName)).thenReturn(mockDataSetProperty);

	return this;
    }

    public PropertyCreator build() {
	return mockPropertyCreator;
    }

    public static PropertyCreator createWithReadOnlyProperty(String propertyName) {
	MockPropertyCreatorBuilder builder = new MockPropertyCreatorBuilder();
	builder.declareReadOnlyProperty(propertyName);
	return builder.build();
    }

    public static PropertyCreator createWithProperty(String propertyName) {
	MockPropertyCreatorBuilder builder = new MockPropertyCreatorBuilder();
	builder.declareProperty(propertyName);
	return builder.build();
    }

    public static PropertyCreator createWithDataSetProperty(String propertyName) {
	MockPropertyCreatorBuilder builder = new MockPropertyCreatorBuilder();
	builder.declareDataSetProperty(propertyName);
	return builder.build();
    }
}
