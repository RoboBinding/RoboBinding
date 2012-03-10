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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.binder.BindingContext;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class GroupedViewAttributeTest
{
	private AbstractGroupedViewAttribute<View> groupedViewAttribute;
	private AbstractViewAttributeInstantiator mockViewAttributeInstantiator;
	private View view;

	@Before
	public void setUp()
	{
		view = mock(View.class);
		mockViewAttributeInstantiator = mock(AbstractViewAttributeInstantiator.class);
		groupedViewAttribute = new GroupedViewAttributeForTest();
	}

	@Test
	public void whenSettingViewListenersProvider_thenSetViewListenersOnViewAttributeInstantiator()
	{
		groupedViewAttribute.setView(view);

		groupedViewAttribute.setViewListenersProvider(mock(ViewListenersProvider.class));

		verify(mockViewAttributeInstantiator).setViewListenersIfRequired(groupedViewAttribute, view);
	}

	@Test
	public void whenGettingDefaultCompulsoryAttributes_thenReturnEmptyArray()
	{
		assertThat(groupedViewAttribute.getCompulsoryAttributes().length, is(0));
	}

	public class GroupedViewAttributeForTest extends AbstractGroupedViewAttribute<View>
	{
		protected AbstractViewAttributeInstantiator getViewAttributeInstantiator()
		{
			return mockViewAttributeInstantiator;
		}

		@Override
		public void bindTo(BindingContext bindingContext)
		{
		}
	}
}
