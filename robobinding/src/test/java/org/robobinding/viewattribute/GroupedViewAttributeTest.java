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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.robobinding.attribute.GroupAttributesBuilder.aGroupAttributes;

import org.junit.Ignore;
import org.junit.Test;
import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class GroupedViewAttributeTest extends ViewAttributeContractTest<AbstractGroupedViewAttribute<View>>
{
	/**
	 * Ignored by Cheng. Will discuss this.
	 */
	@Test@Ignore
	public void whenSettingViewListenersProvider_thenSetViewListenersOnViewAttributeInstantiator()
	{
		//groupedViewAttribute.setView(view);

		//groupedViewAttribute.setViewListenersProvider(mock(ViewListenersProvider.class));

		//verify(mockViewAttributeInstantiator).setViewListenersIfRequired(groupedViewAttribute, view);
	}

	@Test
	public void whenGettingDefaultCompulsoryAttributes_thenReturnEmptyArray()
	{
		AbstractGroupedViewAttribute<View> groupedViewAttribute = new GroupedViewAttributeForTest();
		
		assertThat(groupedViewAttribute.getCompulsoryAttributes().length, is(0));
	}
	
	@Test (expected = AttributeGroupBindingException.class)
	@Override
	public void whenAnExceptionIsThrownDuringPreInitializingView_thenCatchAndRethrow()
	{
		super.whenAnExceptionIsThrownDuringPreInitializingView_thenCatchAndRethrow();
	}
	
	@Override
	protected AbstractGroupedViewAttribute<View> throwsExceptionDuringPreInitializingView()
	{
		return new ThrowsExceptionDuringPreInitializingView();
	}

	@Test (expected = AttributeGroupBindingException.class)
	@Override
	public void whenAnExceptionIsThrownDuringBinding_thenCatchAndRethrow()
	{
		super.whenAnExceptionIsThrownDuringBinding_thenCatchAndRethrow();
	}
	
	@Override
	protected AbstractGroupedViewAttribute<View> throwsExceptionDuringBinding()
	{
		return new ThrowsExceptionDuringBinding();
	}
	
	private static class AbstractGroupedViewAttributeForTest extends AbstractGroupedViewAttribute<View>
	{
		public int childViewAttributesSetupCounter = 0;
		@Override
		protected void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings)
		{
		}
		
		@Override
		protected void setupChildViewAttributes(ChildViewAttributes<View> childViewAttributes, BindingContext bindingContext)
		{
			childViewAttributesSetupCounter++;
		}
	}

	public class GroupedViewAttributeForTest extends AbstractGroupedViewAttributeForTest
	{
		public GroupedViewAttributeForTest()
		{
			childViewAttributes = new ChildViewAttributes<View>(aGroupAttributes().build(), 
					mock(AbstractViewAttributeInitializer.class));
		}
	}
	
	private static class ThrowsExceptionDuringPreInitializingView extends AbstractGroupedViewAttributeForTest
	{
		@SuppressWarnings("unchecked")
		public ThrowsExceptionDuringPreInitializingView()
		{
			view = mock(View.class);
			
			childViewAttributes = mock(ChildViewAttributes.class);
			doThrow(new AttributeGroupBindingException()).when(childViewAttributes).preInitializeView(any(BindingContext.class));
		}
	}
	
	private static class ThrowsExceptionDuringBinding extends AbstractGroupedViewAttributeForTest
	{
		@SuppressWarnings("unchecked")
		public ThrowsExceptionDuringBinding()
		{
			view = mock(View.class);
			
			childViewAttributes = mock(ChildViewAttributes.class);
			doThrow(new AttributeGroupBindingException()).when(childViewAttributes).bindTo(any(BindingContext.class));
		}
	}
}
