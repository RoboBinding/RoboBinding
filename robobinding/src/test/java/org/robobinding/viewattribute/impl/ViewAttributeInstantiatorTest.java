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

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.BindingContext;
import org.robobinding.attribute.GroupedAttributeDetails;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.ViewListenersProvider;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAttributeInstantiatorTest
{
	private ViewAttributeInstantiator viewAttributeInstantiator;
	private ViewListenersProvider viewListenersProvider;
	
	@Before
	public void setUp()
	{
		viewListenersProvider = mock(ViewListenersProvider.class);
		viewAttributeInstantiator = new ViewAttributeInstantiatorForTest(viewListenersProvider);
	}
	
	@Test
	public void whenNewGroupedViewAttribute_thenNewInstanceShouldBeCorrectlyInitialized()
	{
		View view = mock(View.class);
		GroupedAttributeDetails groupedAttributeDetails = mock(GroupedAttributeDetails.class);
		
		MockGroupedViewAttribute groupedViewAttribute = viewAttributeInstantiator.newGroupedViewAttribute(view, MockGroupedViewAttribute.class, groupedAttributeDetails);
		
		groupedViewAttribute.assertCorrectlyInitialized(view, groupedAttributeDetails, viewListenersProvider);
	}
	
	private static class ViewAttributeInstantiatorForTest extends ViewAttributeInstantiator
	{
		public ViewAttributeInstantiatorForTest(ViewListenersProvider viewListenersProvider)
		{
			this.viewListenersProvider = viewListenersProvider;
			viewAttributeInstantiatorImplementor = mock(ViewAttributeInstantiatorImplementor.class);
			when(viewAttributeInstantiatorImplementor.newViewAttribute(MockGroupedViewAttribute.class)).thenReturn(new MockGroupedViewAttribute());
		}
	}
	
	public static class MockGroupedViewAttribute extends AbstractGroupedViewAttribute<View>
	{
		private View view;
		private GroupedAttributeDetails groupedAttributeDetails;
		private ViewListenersProvider viewListenersProvider;
		private boolean postInitializationInvoked;
		@Override
		public void setView(View view)
		{
			this.view = view;
		}
		@Override
		public void setGroupedAttributeDetails(GroupedAttributeDetails groupedAttributeDetails)
		{
			this.groupedAttributeDetails = groupedAttributeDetails;
		}
		
		@Override
		public void setViewListenersProvider(ViewListenersProvider viewListenersProvider)
		{
			this.viewListenersProvider = viewListenersProvider;
		}
		
		@Override
		public void postInitialization()
		{
			postInitializationInvoked = true;
		}
		
		public void assertCorrectlyInitialized(View view, GroupedAttributeDetails groupedAttributeDetails, 
				ViewListenersProvider viewListenersProvider)
		{
			assertThat(this.view, sameInstance(view));
			assertThat(this.groupedAttributeDetails, sameInstance(groupedAttributeDetails));
			assertThat(this.viewListenersProvider, sameInstance(viewListenersProvider));
			assertTrue(postInitializationInvoked);
		}
		
		@Override
		public void bindTo(BindingContext bindingContext)
		{
		}
	}
}
