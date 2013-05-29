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
package org.robobinding.presentationmodel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.ArrayUtils;
import org.robobinding.function.Function;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.viewattribute.BindingAttributeValues;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockPresentationModelAdapterBuilder {
    private PresentationModelAdapter mockPresentationModelAdapter;

    private MockPresentationModelAdapterBuilder() {
	mockPresentationModelAdapter = mock(PresentationModelAdapter.class);
    }

    public static <PropertyType> PresentationModelAdapter aPresentationModelAdapterWithReadOnlyDefaultProperty() {
	MockPresentationModelAdapterBuilder builder = new MockPresentationModelAdapterBuilder();
	builder.<PropertyType> declareReadOnlyProperty(BindingAttributeValues.DEFAULT_PROPERTY_NAME);
	return builder.build();
    }

    public static <PropertyType> PresentationModelAdapter aPresentationModelAdapterWithReadOnlyDefaultProperty(PropertyType propertyValue) {
	MockPresentationModelAdapterBuilder builder = new MockPresentationModelAdapterBuilder();
	builder.<PropertyType> declareReadOnlyProperty(BindingAttributeValues.DEFAULT_PROPERTY_NAME, propertyValue);
	return builder.build();
    }

    public static <PropertyType> PresentationModelAdapter aPresentationModelAdapterWithDefaultProperty() {
	MockPresentationModelAdapterBuilder builder = new MockPresentationModelAdapterBuilder();
	builder.<PropertyType> declareProperty(BindingAttributeValues.DEFAULT_PROPERTY_NAME);
	return builder.build();
    }

    public static <PropertyType> PresentationModelAdapter aPresentationModelAdapterWithDefaultProperty(PropertyType propertyValue) {
	MockPresentationModelAdapterBuilder builder = new MockPresentationModelAdapterBuilder();
	builder.<PropertyType> declareProperty(BindingAttributeValues.DEFAULT_PROPERTY_NAME, propertyValue);
	return builder.build();
    }

    public static MockPresentationModelAdapterBuilder aPresentationModelAdapter() {
	return new MockPresentationModelAdapterBuilder();
    }

    public <PropertyType> MockPresentationModelAdapterBuilder declareReadOnlyProperty(String propertyName) {
	ValueModel<PropertyType> propertyValueModel = ValueModelUtils.create();

	declareReadOnlyProperty(propertyName, propertyValueModel);

	return this;
    }

    public <PropertyType> MockPresentationModelAdapterBuilder declareReadOnlyProperty(String propertyName, PropertyType propertyValue) {
	ValueModel<PropertyType> propertyValueModel = ValueModelUtils.create(propertyValue);

	declareReadOnlyProperty(propertyName, propertyValueModel);

	return this;
    }

    public <PropertyType> MockPresentationModelAdapterBuilder declareReadOnlyProperty(String propertyName, 
	    ValueModel<PropertyType> propertyValueModel) {
	when(mockPresentationModelAdapter.<PropertyType> getReadOnlyPropertyValueModel(propertyName)).thenReturn(propertyValueModel);
	return this;
    }

    public <PropertyType> MockPresentationModelAdapterBuilder declareProperty(String propertyName) {
	ValueModel<PropertyType> propertyValueModel = ValueModelUtils.create();

	declareProperty(propertyName, propertyValueModel);

	return this;
    }

    public <PropertyType> MockPresentationModelAdapterBuilder declareProperty(String propertyName, PropertyType propertyValue) {
	ValueModel<PropertyType> propertyValueModel = ValueModelUtils.create(propertyValue);

	declareProperty(propertyName, propertyValueModel);

	return this;
    }

    private <PropertyType> void declareProperty(String propertyName, ValueModel<PropertyType> propertyValueModel) {
	when(mockPresentationModelAdapter.<PropertyType> getPropertyValueModel(propertyName)).thenReturn(propertyValueModel);
    }

    public MockPresentationModelAdapterBuilder withFunction(String functionName, Class<?>... parameterTypes) {
	Function function = mock(Function.class);
	if (ArrayUtils.isEmpty(parameterTypes)) {
	    when(mockPresentationModelAdapter.findFunction(functionName)).thenReturn(function);
	} else {
	    when(mockPresentationModelAdapter.findFunction(functionName, parameterTypes)).thenReturn(function);
	}
	return this;
    }

    public PresentationModelAdapter build() {
	return mockPresentationModelAdapter;
    }
}
