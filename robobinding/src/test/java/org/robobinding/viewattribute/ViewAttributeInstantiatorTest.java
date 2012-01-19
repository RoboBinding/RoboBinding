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
package org.robobinding.viewattribute;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.content.Context;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewAttributeInstantiatorTest
{
	private final static String ATTRIBUTE_NAME = "attribute_name";
	private final static String ATTRIBUTE_VALUE = "attribute_value";
	
	private ViewAttributeInstantiator<View> viewAttributeInstantiator;
	private View view;
	private boolean preInitializeViews;
	private GroupedAttributeDetails groupedAttributeDetails;
	
	@Before
	public void setUp()
	{
		view = mock(View.class);
		preInitializeViews = RandomValues.trueOrFalse();
		groupedAttributeDetails = mock(GroupedAttributeDetails.class);
		when(groupedAttributeDetails.attributeValueFor(ATTRIBUTE_NAME)).thenReturn(ATTRIBUTE_VALUE);
		viewAttributeInstantiator = new ViewAttributeInstantiator<View>(view, preInitializeViews, groupedAttributeDetails);
	}
	
	@Test
	public void whenInvokingOnPropertyViewAttribute_thenInjectAllPropertiesCorrectly()
	{
		MockPropertyViewAttribute mockPropertyViewAttribute = viewAttributeInstantiator.newPropertyViewAttribute(MockPropertyViewAttribute.class, ATTRIBUTE_NAME);
		
		mockPropertyViewAttribute.assertAllPropertiesAssigned(view, preInitializeViews, ATTRIBUTE_VALUE);
	}
	
	@Test
	public void whenInvokingOnAbstractCommandViewAttribute_thenInjectAllPropertiesCorrectly()
	{
		MockCommandViewAttribute mockCommandViewAttribute = viewAttributeInstantiator.newCommandViewAttribute(MockCommandViewAttribute.class, ATTRIBUTE_NAME);
		
		mockCommandViewAttribute.assertBothPropertiesAssigned(view, ATTRIBUTE_VALUE);
	}
	
	public static class MockPropertyViewAttribute implements PropertyViewAttribute<View>
	{
		private String attributeValue;
		private View view;
		private boolean preInitializeViews;

		@Override
		public void setView(View view)
		{
			this.view = view;
		}

		@Override
		public void setPreInitializeView(boolean preInitializeViews)
		{
			this.preInitializeViews = preInitializeViews;
		}

		@Override
		public void setAttributeValue(String attributeValue)
		{
			this.attributeValue = attributeValue;
		}
		
		public boolean assertAllPropertiesAssigned(View view, boolean preInitializeViews, String attributeValue)
		{
			return this.view == view && this.preInitializeViews == preInitializeViews && this.attributeValue == attributeValue;
		}
		
		@Override
		public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
		{
		}
	}
	
	public static class MockCommandViewAttribute extends AbstractCommandViewAttribute<View>
	{
		private String commandName;

		@Override
		public void setCommandName(String commandName)
		{
			this.commandName = commandName;
		}

		public boolean assertBothPropertiesAssigned(View view, String commandName)
		{
			return this.view == view && this.commandName == commandName;
		}
		
		@Override
		protected void bind(Command command)
		{
		}

		@Override
		protected Class<?> getPreferredCommandParameterType()
		{
			return null;
		}
		
	}
	
}
