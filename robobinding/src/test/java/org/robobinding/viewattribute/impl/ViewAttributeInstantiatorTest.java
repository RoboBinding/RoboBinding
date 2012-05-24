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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.ChildAttributeResolvers;
import org.robobinding.attribute.GroupedAttributeDescriptor;
import org.robobinding.attribute.PlainAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.GroupedAttributeDetails;
import org.robobinding.viewattribute.ViewListenersProvider;

import android.view.View;

import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAttributeInstantiatorTest
{
	private static final String PLAIN_ATTRIBUTE_NAME = "name";
	private static final String PLAIN_ATTRIBUTE_VALUE = "value";
	
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
		
		MockGroupedViewAttribute groupedViewAttribute = viewAttributeInstantiator.newGroupedViewAttribute(
				view, MockGroupedViewAttribute.class, newGroupedAttributeDescriptor());
		
		groupedViewAttribute.assertCorrectlyInitialized(view, viewListenersProvider);
	}
	
	private GroupedAttributeDescriptor newGroupedAttributeDescriptor()
	{
		Map<String, String> presentAttributeMappings = Maps.newHashMap();
		presentAttributeMappings.put(PLAIN_ATTRIBUTE_NAME, PLAIN_ATTRIBUTE_VALUE);
		return new GroupedAttributeDescriptor(presentAttributeMappings);
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
		private ViewListenersProvider viewListenersProvider;
		private boolean mapChildAttributeResolversInvoked = false;
		
		@Override
		public void setViewListenersProvider(ViewListenersProvider viewListenersProvider)
		{
			this.viewListenersProvider = viewListenersProvider;
		}
		
		@Override
		public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings)
		{
			resolverMappings.map(ChildAttributeResolvers.plainAttributeResolver(), PLAIN_ATTRIBUTE_NAME);
			mapChildAttributeResolversInvoked = true;
		}
		
		public void assertCorrectlyInitialized(View view, ViewListenersProvider viewListenersProvider)
		{
			assertThat(this.view, sameInstance(view));
			assertThat(groupedAttribute.plainAttribute(PLAIN_ATTRIBUTE_NAME), equalTo(new PlainAttribute(PLAIN_ATTRIBUTE_NAME, PLAIN_ATTRIBUTE_VALUE)));
			assertThat(this.viewListenersProvider, sameInstance(viewListenersProvider));
			assertTrue(mapChildAttributeResolversInvoked);
		}

		@Override
		protected void setupChildAttributeBindings(ChildAttributeBindings binding)
		{
		}
		
	}
}
