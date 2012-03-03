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
import static org.mockito.Mockito.mock;

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
public class BindingAttributeMappingsImplTest
{
	private View view;
	private boolean preInitializeViews;
	private ViewListenersProvider viewListenersProvider;
	private BindingAttributeMappingsImplForTest bindingAttributeMappingsImpl;
	private GroupedAttributeDetailsImpl groupedAttributeDetailsImpl;

	@Before
	public void setUp()
	{
		view = mock(View.class);
		preInitializeViews = RandomValues.trueOrFalse();
		viewListenersProvider = mock(ViewListenersProvider.class);
		bindingAttributeMappingsImpl = new BindingAttributeMappingsImplForTest(view, preInitializeViews, viewListenersProvider);
		groupedAttributeDetailsImpl = mock(GroupedAttributeDetailsImpl.class);
	}

	@Test
	public void whenCreatingAGroupedViewAttribute_thenAllPropertiesShouldBeAssigned()
	{
		addGroupedViewAttributeMapping();

		MockGroupedViewAttribute mockGroupedViewAttribute = (MockGroupedViewAttribute)bindingAttributeMappingsImpl.createGroupedViewAttribute(groupedAttributeDetailsImpl);

		mockGroupedViewAttribute.assertAllPropertiesSet(view, preInitializeViews, groupedAttributeDetailsImpl, viewListenersProvider);
	}

	private void addGroupedViewAttributeMapping()
	{
		bindingAttributeMappingsImpl.addGroupedViewAttributeMapping(groupedAttributeDetailsImpl, MockGroupedViewAttribute.class);
	}

	public static class MockGroupedViewAttribute extends AbstractGroupedViewAttribute<View>
	{
		private View view;
		private boolean preInitializeViews;
		private GroupedAttributeDetails groupedAttributeDetails;
		private ViewListenersProvider viewListenersProvider;

		@Override
		public void setView(View view)
		{
			this.view = view;
		}

		@Override
		public void setPreInitializeViews(boolean preInitializeViews)
		{
			this.preInitializeViews = preInitializeViews;
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

		public void assertAllPropertiesSet(View view, boolean preInitializeViews, GroupedAttributeDetailsImpl groupedAttributeDetailsImpl,
				ViewListenersProvider viewListenersProvider)
		{
			assertTrue(this.view == view && this.preInitializeViews == preInitializeViews && this.groupedAttributeDetails == groupedAttributeDetailsImpl && this.viewListenersProvider == viewListenersProvider);
		}

		@Override
		public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
		{
		}
	}

	public static class BindingAttributeMappingsImplForTest extends BindingAttributeMappingsImpl<View>
	{
		public BindingAttributeMappingsImplForTest(View view, boolean preInitializeViews, ViewListenersProvider viewListenersProvider)
		{
			super(view, preInitializeViews, viewListenersProvider);
		}
	}
}
