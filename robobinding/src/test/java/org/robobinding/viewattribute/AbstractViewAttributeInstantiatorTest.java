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

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.BindingContext;
import org.robobinding.viewattribute.view.ViewListeners;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class AbstractViewAttributeInstantiatorTest
{
	private final static String ATTRIBUTE_NAME = "attribute_name";
	private final static String ATTRIBUTE_VALUE = "attribute_value";
	
	private AbstractViewAttributeInstantiator viewAttributeInstantiator;
	private View view;
	private ViewListenersProvider viewListenersProvider;
	private ViewListeners viewListeners;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp()
	{
		view = mock(View.class);
		viewListenersProvider = mock(ViewListenersProvider.class);
		viewListeners = new ViewListeners(view);
		when(viewListenersProvider.forViewAndAttribute(eq(view), any(ViewListenersAware.class))).thenReturn(viewListeners);
		viewAttributeInstantiator = new ViewAttributeInstantiatorForTest();
	}
	
	@Test
	public void whenInvokingOnPropertyViewAttribute_thenInjectAllPropertiesCorrectly()
	{
		MockPropertyViewAttribute mockPropertyViewAttribute = viewAttributeInstantiator.newPropertyViewAttribute(MockPropertyViewAttribute.class, ATTRIBUTE_NAME);
		
		mockPropertyViewAttribute.assertAllPropertiesAssigned(view, ATTRIBUTE_VALUE);
	}
	
	@Test
	public void whenInvokingOnViewListenersAwarePropertyViewAttribute_thenInjectAllPropertiesCorrectly()
	{
		MockViewListenersAwarePropertyViewAttribute mockPropertyViewAttribute = viewAttributeInstantiator.newPropertyViewAttribute(MockViewListenersAwarePropertyViewAttribute.class, ATTRIBUTE_NAME);
		
		mockPropertyViewAttribute.assertAllPropertiesAssigned(view, ATTRIBUTE_VALUE, viewListeners);
	}
	
	@Test
	public void whenInvokingOnAbstractCommandViewAttribute_thenInjectAllPropertiesCorrectly()
	{
		MockCommandViewAttribute mockCommandViewAttribute = viewAttributeInstantiator.newCommandViewAttribute(MockCommandViewAttribute.class, ATTRIBUTE_NAME);
		
		mockCommandViewAttribute.assertBothPropertiesAssigned(view, ATTRIBUTE_VALUE);
	}
	
	@Test
	public void whenInvokingOnViewListenersAwareAbstractCommandViewAttribute_thenInjectAllPropertiesCorrectly()
	{
		MockViewListenersAwareCommandViewAttribute mockCommandViewAttribute = viewAttributeInstantiator.newCommandViewAttribute(MockViewListenersAwareCommandViewAttribute.class, ATTRIBUTE_NAME);
		
		mockCommandViewAttribute.assertAllPropertiesAssigned(view, ATTRIBUTE_VALUE, viewListeners);
	}
	
	public class ViewAttributeInstantiatorForTest extends AbstractViewAttributeInstantiator
	{
		public ViewAttributeInstantiatorForTest()
		{
			super(AbstractViewAttributeInstantiatorTest.this.viewListenersProvider);
		}
		
		@Override
		protected String attributeValueFor(String attribute)
		{
			return ATTRIBUTE_VALUE;
		}
		
		@Override
		protected View getView()
		{
			return view;
		}
	}
	
	public static class MockPropertyViewAttribute implements PropertyViewAttribute<View>
	{
		private String attributeValue;
		private View view;

		@Override
		public void setView(View view)
		{
			this.view = view;
		}

		@Override
		public void setAttributeValue(String attributeValue)
		{
			this.attributeValue = attributeValue;
		}
		
		public void assertAllPropertiesAssigned(View view, String attributeValue)
		{
			assertTrue(this.view == view && this.attributeValue == attributeValue);
		}
		
		@Override
		public void bindTo(BindingContext bindingContext)
		{
		}
	}
	
	public static class MockViewListenersAwarePropertyViewAttribute extends MockPropertyViewAttribute implements ViewListenersAware<ViewListeners>
	{
		private ViewListeners viewListeners;

		@Override
		public void setViewListeners(ViewListeners viewListeners)
		{
			this.viewListeners = viewListeners;
		}

		public void assertAllPropertiesAssigned(View view, String attributeValue, ViewListeners viewListeners)
		{
			super.assertAllPropertiesAssigned(view, attributeValue);
			assertTrue(this.viewListeners == viewListeners);
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

		public void assertBothPropertiesAssigned(View view, String commandName)
		{
			assertTrue(this.view == view && this.commandName == commandName);
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
	
	public static class MockViewListenersAwareCommandViewAttribute extends MockCommandViewAttribute implements ViewListenersAware<ViewListeners>
	{
		private ViewListeners viewListeners;

		@Override
		public void setViewListeners(ViewListeners viewListeners)
		{
			this.viewListeners = viewListeners;
		}
		
		public void assertAllPropertiesAssigned(View view, String commandName, ViewListeners viewListeners)
		{
			super.assertBothPropertiesAssigned(view, commandName);
			assertTrue(this.viewListeners == viewListeners);
		}
	}
	
}
