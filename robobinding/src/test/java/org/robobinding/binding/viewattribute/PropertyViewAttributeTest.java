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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.binding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.binding.viewattribute.PropertyBindingDetails;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.PropertyValueModel;

import android.content.Context;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class PropertyViewAttributeTest
{
	private static final String PROPERTY_NAME = "property_name";
	private static final boolean ONE_WAY_BINDING = false;
	private static final boolean TWO_WAY_BINDING = true;
	private static final boolean PRE_INITIALIZE_VIEW = true;
	private static final boolean DONT_PRE_INITIALIZE_VIEW = false;
	
	private PresentationModelAdapter presentationModelAdapter;
	private Context context;
	private PropertyValueModel valueModel = mock(PropertyValueModel.class);
	private DummyPropertyViewAttribute propertyViewAttributeValue;
	
	@Before
	public void setUp()
	{
		presentationModelAdapter = mock(PresentationModelAdapter.class);
	}
	
	@Test
	public void given1WayBinding_ThenBindToReadOnlyPropertyFromPresentationModelAdapter()
	{
		initializePropertyViewAttribute(ONE_WAY_BINDING, DONT_PRE_INITIALIZE_VIEW);
		when(presentationModelAdapter.getReadOnlyPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
		
		propertyViewAttributeValue.bind(presentationModelAdapter, context);
		
		assertThat(propertyViewAttributeValue.valueModelBound, equalTo(valueModel));
	}
	
	@Test
	public void given2WayBinding_ThenBindToReadWritePropertyFromPresentationModelAdapter()
	{
		initializePropertyViewAttribute(TWO_WAY_BINDING, DONT_PRE_INITIALIZE_VIEW);
		when(presentationModelAdapter.getPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
		
		propertyViewAttributeValue.bind(presentationModelAdapter, context);
		
		assertThat(propertyViewAttributeValue.valueModelBound, equalTo(valueModel));
	}
	
	@Test
	public void givenNoPreInitializeViewFlag_ThenInitializeTheViewToReflectTheValueModel()
	{
		initializePropertyViewAttribute(ONE_WAY_BINDING, DONT_PRE_INITIALIZE_VIEW);
		when(presentationModelAdapter.getReadOnlyPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
		
		propertyViewAttributeValue.bind(presentationModelAdapter, context);
		
		assertFalse(propertyViewAttributeValue.viewInitialized);
	}
	
	@Test
	public void givenPreInitializeViewFlag_ThenInitializeTheViewToReflectTheValueModel()
	{
		initializePropertyViewAttribute(ONE_WAY_BINDING, PRE_INITIALIZE_VIEW);
		when(presentationModelAdapter.getReadOnlyPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
		
		propertyViewAttributeValue.bind(presentationModelAdapter, context);
		
		assertTrue(propertyViewAttributeValue.viewInitialized);
	}
	
	private void initializePropertyViewAttribute(boolean binding, boolean preInitializeView)
	{
		PropertyBindingDetails propertyBindingDetails = new PropertyBindingDetails(PROPERTY_NAME, binding, preInitializeView);
		propertyViewAttributeValue = new DummyPropertyViewAttribute(propertyBindingDetails);
	}
	
	private static class DummyPropertyViewAttribute extends AbstractPropertyViewAttribute
	{
		private BindingType bindingType = BindingType.NO_BINDING;
		private PropertyValueModel valueModelBound;
		private boolean viewInitialized;
		
		public DummyPropertyViewAttribute(PropertyBindingDetails propertyBindingDetails)
		{
			super(propertyBindingDetails);
		}
		
		@Override
		void observeChangesOnTheValueModel(PropertyValueModel valueModel)
		{
			if (bindingType != BindingType.TWO_WAY)
				bindingType = BindingType.ONE_WAY;
		
			valueModelBound = valueModel;
		}

		@Override
		protected void observeChangesOnTheView(PropertyValueModel valueModel)
		{
			bindingType = BindingType.TWO_WAY;
		}

		@Override
		protected void valueModelUpdated(Object newValue)
		{
			viewInitialized = true;
		}
	}
}
