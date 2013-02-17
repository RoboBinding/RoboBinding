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
import static org.robobinding.attribute.MockCommandAttributeBuilder.aCommandAttribute;
import static org.robobinding.attribute.MockValueModelAttributeBuilder.aValueModelAttribute;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.BindingContext;
import org.robobinding.attribute.Command;
import org.robobinding.attribute.CommandAttribute;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.view.ViewListeners;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class AbstractViewAttributeInitializerForTest
{
	private AbstractViewAttributeInitializer viewAttributeInstantiator;
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
		viewAttributeInstantiator = new ViewAttributeInitializerForTest();
	}
	
	@Test
	public void whenInvokingOnPropertyViewAttribute_thenInjectAllPropertiesCorrectly()
	{
		ValueModelAttribute attribute = aValueModelAttribute();
		
		MockPropertyViewAttribute mockPropertyViewAttribute = viewAttributeInstantiator.newPropertyViewAttribute(new MockPropertyViewAttribute(), attribute);
		
		mockPropertyViewAttribute.assertAllPropertiesAssigned(view, attribute);
	}
	
	@Test
	public void whenInvokingOnViewListenersAwarePropertyViewAttribute_thenInjectAllPropertiesCorrectly()
	{
		ValueModelAttribute attribute = aValueModelAttribute();
		
		MockViewListenersAwarePropertyViewAttribute mockPropertyViewAttribute = viewAttributeInstantiator.newPropertyViewAttribute(new MockViewListenersAwarePropertyViewAttribute(), attribute);
		
		mockPropertyViewAttribute.assertAllPropertiesAssigned(view, attribute, viewListeners);
	}
	
	@Test
	public void whenInvokingOnAbstractCommandViewAttribute_thenInjectAllPropertiesCorrectly()
	{
		CommandAttribute attribute = aCommandAttribute();
		
		MockCommandViewAttribute mockCommandViewAttribute = viewAttributeInstantiator.newCommandViewAttribute(new MockCommandViewAttribute(), attribute);
		
		mockCommandViewAttribute.assertBothPropertiesAssigned(view, attribute);
	}
	
	@Test
	public void whenInvokingOnViewListenersAwareAbstractCommandViewAttribute_thenInjectAllPropertiesCorrectly()
	{
		CommandAttribute attribute = aCommandAttribute();
		
		MockViewListenersAwareCommandViewAttribute mockCommandViewAttribute = viewAttributeInstantiator.newCommandViewAttribute(new MockViewListenersAwareCommandViewAttribute(), attribute);
		
		mockCommandViewAttribute.assertAllPropertiesAssigned(view, attribute, viewListeners);
	}
	
	public class ViewAttributeInitializerForTest extends AbstractViewAttributeInitializer
	{
		public ViewAttributeInitializerForTest()
		{
			super(AbstractViewAttributeInitializerForTest.this.viewListenersProvider);
		}
		
		@Override
		protected View getView()
		{
			return view;
		}
	}
	
	public static class MockPropertyViewAttribute implements PropertyViewAttribute<View>
	{
		private ValueModelAttribute attribute;
		private View view;

		@Override
		public void setView(View view)
		{
			this.view = view;
		}
		
		@Override
		public void setAttribute(ValueModelAttribute attribute)
		{
			this.attribute = attribute;
		}
		
		public void assertAllPropertiesAssigned(View view, ValueModelAttribute attribute)
		{
			assertTrue((this.view == view) && (this.attribute == attribute));
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

		public void assertAllPropertiesAssigned(View view, ValueModelAttribute attribute, ViewListeners viewListeners)
		{
			super.assertAllPropertiesAssigned(view, attribute);
			assertTrue(this.viewListeners == viewListeners);
		}
	}
	
	public static class MockCommandViewAttribute extends AbstractCommandViewAttribute<View>
	{
		private CommandAttribute attribute;

		@Override
		public void setAttribute(CommandAttribute attribute)
		{
			this.attribute = attribute;
		}

		public void assertBothPropertiesAssigned(View view, CommandAttribute attribute)
		{
			assertTrue((this.view == view) && (this.attribute == attribute));
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
		
		public void assertAllPropertiesAssigned(View view, CommandAttribute attribute, ViewListeners viewListeners)
		{
			super.assertBothPropertiesAssigned(view, attribute);
			assertTrue(this.viewListeners == viewListeners);
		}
	}
	
}
