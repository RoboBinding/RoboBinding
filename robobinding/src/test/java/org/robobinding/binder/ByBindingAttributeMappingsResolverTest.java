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
package org.robobinding.binder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robobinding.binder.MockBindingAttributeMappingsImplBuilder.aBindingAttributeMappingsImpl;
import static org.robobinding.binder.MockBindingAttributeMappingsImplBuilder.aCommandViewAttribute;
import static org.robobinding.binder.MockBindingAttributeMappingsImplBuilder.aGroupedViewAttribute;
import static org.robobinding.binder.MockBindingAttributeMappingsImplBuilder.aPropertyViewAttribute;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.PendingAttributesForView;
import org.robobinding.ViewResolutionError;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;

import android.view.View;

import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ByBindingAttributeMappingsResolverTest
{
	private PendingAttributesForView pendingAttributesForView;

	@Before
	public void setUp()
	{
		pendingAttributesForView = new MockPendingAttributesForView();
	}

	@Test
	public void givenAPropertyAttribute_whenResolve_thenAResolvedPropertyViewAttributeShouldBeReturned()
	{
		String propertyAttribute = "propertyAttribute";
		ByBindingAttributeMappingsResolver byBindingAttributeMappingsResolver = newByBindingAttributeMappingsResolver(aBindingAttributeMappingsImpl()
				.withPropertyAttribute(propertyAttribute)
				.build());

		Collection<ViewAttribute> viewAttributes = byBindingAttributeMappingsResolver.resolve(pendingAttributesForView);

		assertThat(Sets.newHashSet(viewAttributes), equalTo(Sets.<ViewAttribute> newHashSet(aPropertyViewAttribute(propertyAttribute))));
	}

	@Test
	public void givenACommandAttribute_whenResolve_thenAResolvedCommandViewAttributeShouldBeReturned()
	{
		String commandAttribute = "commandAttribute";
		ByBindingAttributeMappingsResolver byBindingAttributeMappingsResolver = newByBindingAttributeMappingsResolver(aBindingAttributeMappingsImpl()
				.withCommandAttribute(commandAttribute)
				.build());
		
		Collection<ViewAttribute> viewAttributes = byBindingAttributeMappingsResolver.resolve(pendingAttributesForView);

		assertThat(Sets.newHashSet(viewAttributes), equalTo(Sets.<ViewAttribute> newHashSet(aCommandViewAttribute(commandAttribute))));
	}

	@Test
	public void givenAAttributeGroup_whenResolve_thenAResolvedGroupedViewAttributeShouldBeReturned()
	{
		String[] attributeGroup = { "group_attribute1", "group_attribute2" };
		ByBindingAttributeMappingsResolver byBindingAttributeMappingsResolver = newByBindingAttributeMappingsResolver(aBindingAttributeMappingsImpl()
				.withAttributeGroup(attributeGroup)
				.build());

		Collection<ViewAttribute> viewAttributes = byBindingAttributeMappingsResolver.resolve(pendingAttributesForView);

		assertThat(Sets.newHashSet(viewAttributes), equalTo(Sets.<ViewAttribute> newHashSet(aGroupedViewAttribute(attributeGroup))));
	}
	
	private ByBindingAttributeMappingsResolver newByBindingAttributeMappingsResolver(BindingAttributeMappingsImpl<View> bindingAttributeMappings)
	{
		return new ByBindingAttributeMappingsResolver(bindingAttributeMappings);
	}

	private static class MockPendingAttributesForView implements PendingAttributesForView
	{
		@Override
		public void resolveAttributeGroupIfExists(String[] attributeGroup, AttributeGroupResolver attributeGroupResolver)
		{
			attributeGroupResolver.resolve(null, attributeGroup, null);
		}

		@Override
		public void resolveAttributeIfExists(String attribute, AttributeResolver attributeResolver)
		{
			attributeResolver.resolve(null, attribute, null);
		}

		@Override
		public View getView()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public ViewResolutionError resolveCompleted()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEmpty()
		{
			throw new UnsupportedOperationException();
		}
	}
}