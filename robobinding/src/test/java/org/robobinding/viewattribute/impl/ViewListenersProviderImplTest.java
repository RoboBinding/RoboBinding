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
package org.robobinding.viewattribute.impl;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.ViewListenersProvider;
import org.robobinding.viewattribute.impl.ViewListenersProviderImpl;
import org.robobinding.viewattribute.view.ViewListeners;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class ViewListenersProviderImplTest
{
	@DataPoints
	public static ViewListenersAttribute[] sampleData = new ViewListenersAttribute[]{
		new ViewListenersAttribute(new MockViewListenersAwareAttribute()),
		new ViewListenersAttribute(new MockViewListenersAwareAttributeSubClass()),
		new ViewListenersAttribute(new MockViewListenersAwareAttributeImplementingAnotherInterface()),
		new ViewListenersAttribute(new MockViewListenersAwareAttributeSubClassImplementingAnotherInterface())};
	
	private ViewListenersProvider viewListenersProvider;
	
	@Before
	public void setUp()
	{
		viewListenersProvider = new ViewListenersProviderImpl();
	}

	@Theory
	public void whenAskingForView_thenReturnViewListenerOfCorrectType(ViewListenersAttribute viewAndViewListenersType)
	{
		ViewListeners viewListeners = viewListenersProvider.forViewAndAttribute(viewAndViewListenersType.view, viewAndViewListenersType.viewListenersAware);

		assertThat(viewListeners, instanceOf(viewAndViewListenersType.viewListenersType));
	}
	
	@Theory
	public void whenAskingForViewListenersAgain_thenReturnTheSameInstance(ViewListenersAttribute viewAndViewListenersType)
	{
		ViewListeners viewListeners1 = viewListenersProvider.forViewAndAttribute(viewAndViewListenersType.view, viewAndViewListenersType.viewListenersAware);
		ViewListeners viewListeners2 = viewListenersProvider.forViewAndAttribute(viewAndViewListenersType.view, viewAndViewListenersType.viewListenersAware);

		assertThat(viewListeners1, sameInstance(viewListeners2));
	}
	
	@Test
	public void whenAskingForTwoDifferentViewListenersForTheSameView_thenReturnTheCorrectInstances()
	{
		View view = mock(View.class);
		ViewListenersAware<?> mockViewListenersAware = new MockViewListenersAwareAttribute();
		ViewListenersAware<?> mockViewListenersSubClassAware = new MockViewListenersSubclassAwareAttribute();
		
		ViewListeners viewListeners = viewListenersProvider.forViewAndAttribute(view, mockViewListenersAware);
		ViewListeners mockViewListeners = viewListenersProvider.forViewAndAttribute(view, mockViewListenersSubClassAware);
	
		assertThat(viewListeners, instanceOf(ViewListeners.class));
		assertThat(mockViewListeners, instanceOf(MockViewListenersSubclass.class));
	}
	
	private static class ViewListenersAttribute
	{
		private final ViewListenersAware<?> viewListenersAware;
		private final View view;
		private final Class<? extends ViewListeners> viewListenersType;
		public ViewListenersAttribute(AbstractMockViewListenersAwareAttribute viewListenersAware)
		{
			this.viewListenersAware = (ViewListenersAware<?>)viewListenersAware;
			this.view = mock(View.class);
			this.viewListenersType = viewListenersAware.getViewListenersType();
		}
	}
	
	private abstract static class AbstractMockViewListenersAwareAttribute
	{
		public abstract Class<? extends ViewListeners> getViewListenersType();
	}
	
	private static class MockViewListenersAwareAttribute extends AbstractMockViewListenersAwareAttribute implements ViewListenersAware<ViewListeners>
	{
		@Override
		public void setViewListeners(ViewListeners viewListeners)
		{
		}
		
		public Class<? extends ViewListeners> getViewListenersType()
		{
			return ViewListeners.class;
		}
	}
	
	private static class MockViewListenersSubclassAwareAttribute extends AbstractMockViewListenersAwareAttribute implements ViewListenersAware<MockViewListenersSubclass>
	{
		@Override
		public void setViewListeners(MockViewListenersSubclass viewListeners)
		{
		}
		
		public Class<? extends ViewListeners> getViewListenersType()
		{
			return MockViewListenersSubclass.class;
		}
	}
	
	private static class MockViewListenersAwareAttributeImplementingAnotherInterface extends AbstractMockViewListenersAwareAttribute implements Serializable, ViewListenersAware<ViewListeners>
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void setViewListeners(ViewListeners viewListeners)
		{
		}
		
		public Class<? extends ViewListeners> getViewListenersType()
		{
			return ViewListeners.class;
		}
	}
	
	private static class MockViewListenersAwareAttributeSubClass extends MockViewListenersAwareAttribute
	{
	}
	
	private static class MockViewListenersAwareAttributeSubClassImplementingAnotherInterface extends MockViewListenersAwareAttribute implements Serializable
	{
		private static final long serialVersionUID = 1L;
	}
	
	public static class MockViewListenersSubclass extends ViewListeners
	{
		public MockViewListenersSubclass(View view)
		{
			super(view);
		}
	}
}
