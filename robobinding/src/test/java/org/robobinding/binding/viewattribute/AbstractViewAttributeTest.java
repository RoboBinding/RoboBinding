/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package org.robobinding.binding.viewattribute;

import org.junit.Before;
import org.robobinding.binding.ViewAttribute;
import org.robobinding.function.Function;
import org.robobinding.internal.org_apache_commons_lang3.Validate;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.DataSetProperty;
import org.robobinding.property.PropertyValueModel;
import org.robobinding.property.ValueModelUtils;

import android.app.Activity;
import android.view.View;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class AbstractViewAttributeTest
{
	protected View view;
	protected ShadowView shadowView;
	@Before
	public void setUp()
	{
		view = new View(new Activity());
		shadowView = Robolectric.shadowOf(view);
	}
	protected <T> MockPresentationModelAdapterForProperty<T> bindToProperty(ViewAttribute backgroundAttribute, Class<T> propertyType)
	{
		MockPresentationModelAdapterForProperty<T> mockPresentationModelAdapter = new MockPresentationModelAdapterForProperty<T>(propertyType);
		backgroundAttribute.bind(mockPresentationModelAdapter, new Activity());
		return mockPresentationModelAdapter;
	}
	
	public class MockPresentationModelAdapterForProperty<T> implements PresentationModelAdapter
	{
		private static final String PROPERTY_NAME = "propertyName";
		public static final String ONE_WAY_BINDING_PROPERTY_NAME = "{"+PROPERTY_NAME+"}";
		
		private Class<?> propertyType;
		private PropertyValueModel<T> propertyValueModel;
		private MockPresentationModelAdapterForProperty(Class<T> propertyType)
		{
			this.propertyType = propertyType;
			propertyValueModel = ValueModelUtils.create();
		}
		
		public void updatePropertyValue(T newValue)
		{
			propertyValueModel.setValue(newValue);
		}
		
		@Override
		public Class<?> getPropertyType(String propertyName)
		{
			checkPropertyName(propertyName);
			return propertyType;
		}

		@SuppressWarnings("unchecked")
		@Override
		public PropertyValueModel<T> getReadOnlyPropertyValueModel(String propertyName)
		{
			checkPropertyName(propertyName);
			return propertyValueModel;
		}

		@SuppressWarnings("unchecked")
		@Override
		public PropertyValueModel<T> getPropertyValueModel(String propertyName)
		{
			checkPropertyName(propertyName);
			return propertyValueModel;
		}

		private void checkPropertyName(String propertyName)
		{
			Validate.isTrue(PROPERTY_NAME.equals(propertyName), "invalid property name");
		}

		@Override
		public DataSetProperty<T> getDataSetPropertyValueModel(String propertyName)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public Function findFunction(String functionName, Class<?>... parameterTypes)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public Class<?> getPresentationModelClass()
		{
			throw new UnsupportedOperationException();
		}
	}
}
