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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robobinding.attribute.MockCommandAttributeBuilder.aCommandAttribute;
import static org.robobinding.attribute.MockValueModelAttributeBuilder.aValueModelAttribute;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.attribute.Command;
import org.robobinding.attribute.CommandAttribute;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.property.ValueModel;
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
	private AbstractViewAttributeInitializer viewAttributeInitializer;
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
		viewAttributeInitializer = new ViewAttributeInitializerForTest();
	}
	
	@Test
	public void whenInvokingOnPropertyViewAttribute_thenViewAttributeCorrectlyInitialized()
	{
		ValueModelAttribute attribute = aValueModelAttribute();
		@SuppressWarnings("unchecked")
		AbstractPropertyViewAttribute<View, Object> viewAttribute = mock(AbstractPropertyViewAttribute.class);
		
		viewAttribute = viewAttributeInitializer.initializePropertyViewAttribute(viewAttribute, attribute);
		
		verify(viewAttribute).initialize(eq(new PropertyViewAttributeConfig<View>(view, attribute)));
	}
	
	@Test
	public void whenInvokingOnViewListenersAwarePropertyViewAttribute_thenViewListenersCorrectlySet()
	{
		ValueModelAttribute attribute = aValueModelAttribute();
		MockViewListenersAwarePropertyViewAttribute viewAttribute = mock(MockViewListenersAwarePropertyViewAttribute.class);
		
		viewAttribute = viewAttributeInitializer.initializePropertyViewAttribute(viewAttribute, attribute);
		
		verify(viewAttribute).setViewListeners(viewListeners);
	}
	
	@Test
	public void whenInvokingOnMultiTypePropertyViewAttribute_thenViewAttributeCorrectlyInitialized()
	{
		ValueModelAttribute attribute = aValueModelAttribute();
		@SuppressWarnings("unchecked")
		AbstractMultiTypePropertyViewAttribute<View> viewAttribute = mock(AbstractMultiTypePropertyViewAttribute.class);
		
		viewAttribute = viewAttributeInitializer.initializePropertyViewAttribute(viewAttribute, attribute);
		
		verify(viewAttribute).initialize(eq(new MultiTypePropertyViewAttributeConfig<View>(view, attribute, viewListenersProvider)));
	}
	
	@Test
	public void whenInvokingOnAbstractCommandViewAttribute_thenViewAttributeCorrectlyInitialized()
	{
		CommandAttribute attribute = aCommandAttribute();
		@SuppressWarnings({ "unchecked"})
		AbstractCommandViewAttribute<View> viewAttribute = mock(AbstractCommandViewAttribute.class);
		
		viewAttribute = viewAttributeInitializer.initializeCommandViewAttribute(viewAttribute, attribute);
		
		verify(viewAttribute).initialize(new CommandViewAttributeConfig<View>(view, attribute));
	}
	
	@Test
	public void whenInvokingOnViewListenersAwareAbstractCommandViewAttribute_thenViewListenersCorrectlySet()
	{
		CommandAttribute attribute = aCommandAttribute();
		MockViewListenersAwareCommandViewAttribute viewAttribute = mock(MockViewListenersAwareCommandViewAttribute.class);
		
		viewAttribute = viewAttributeInitializer.initializeCommandViewAttribute(viewAttribute, attribute);
		
		verify(viewAttribute).setViewListeners(viewListeners);
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
	
	public static class MockViewListenersAwarePropertyViewAttribute extends AbstractPropertyViewAttribute<View, Object> implements ViewListenersAware<ViewListeners>
	{
		@Override
		public void setViewListeners(ViewListeners viewListeners)
		{
		}

		@Override
		protected void valueModelUpdated(Object newValue)
		{
		}

		@Override
		protected void observeChangesOnTheView(ValueModel<Object> valueModel)
		{
		}
	}
	
	public static class MockViewListenersAwareCommandViewAttribute extends AbstractCommandViewAttribute<View> implements ViewListenersAware<ViewListeners>
	{
		@Override
		public void setViewListeners(ViewListeners viewListeners)
		{
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
