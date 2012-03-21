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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;
import org.robobinding.BindingContext;
import org.robobinding.PendingAttributesForView;
import org.robobinding.binder.ByBindingAttributeMappingsResolver;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.Command;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;

import android.view.View;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ByBindingAttributeMappingsResolverTest
{
	private ByBindingAttributeMappingsResolver byBindingAttributeMappingsResolver;
	private PendingAttributesForView pendingAttributesForView;
	private List<String> propertyAttributes;
	private List<String> commandAttributes;
	private List<String[]> attributeGroups;

	@Before
	public void setUp()
	{
		propertyAttributes = Lists.newArrayList();
		commandAttributes = Lists.newArrayList();
		attributeGroups = Lists.newArrayList();

		byBindingAttributeMappingsResolver = new ByBindingAttributeMappingsResolver(new MockBindingAttributeMappingsImpl<View>());
		pendingAttributesForView = new MockViewPendingAttribute();
	}

	@Test
	public void givenAPropertyAttribute_whenResolve_thenAResolvedPropertyViewAttributeShouldBeReturned()
	{
		String propertyAttribute = "propertyAttribute";
		propertyAttributes.add(propertyAttribute);

		Collection<ViewAttribute> viewAttributes = byBindingAttributeMappingsResolver.resolve(pendingAttributesForView);

		assertThat(Sets.newHashSet(viewAttributes), equalTo(Sets.<ViewAttribute> newHashSet(new MockPropertyViewAttribute(propertyAttribute))));
	}

	@Test
	public void givenACommandAttribute_whenResolve_thenAResolvedCommandViewAttributeShouldBeReturned()
	{
		String commandAttribute = "commandAttribute";
		commandAttributes.add(commandAttribute);

		Collection<ViewAttribute> viewAttributes = byBindingAttributeMappingsResolver.resolve(pendingAttributesForView);

		assertThat(Sets.newHashSet(viewAttributes), equalTo(Sets.<ViewAttribute> newHashSet(new MockCommandViewAttribute(commandAttribute))));
	}

	@Test
	public void givenAAttributeGroup_whenResolve_thenAResolvedGroupedViewAttributeShouldBeReturned()
	{
		String[] attributeGroup = { "group_attribute1", "group_attribute2" };
		attributeGroups.add(attributeGroup);

		Collection<ViewAttribute> viewAttributes = byBindingAttributeMappingsResolver.resolve(pendingAttributesForView);

		assertThat(Sets.newHashSet(viewAttributes), equalTo(Sets.<ViewAttribute> newHashSet(new MockGroupedViewAttribute(attributeGroup))));
	}

	private class MockBindingAttributeMappingsImpl<T extends View> extends BindingAttributeMappingsImpl<T>
	{
		public MockBindingAttributeMappingsImpl()
		{
			super(null);
		}

		@Override
		public Iterable<String> getPropertyAttributes()
		{
			return propertyAttributes;
		}

		@Override
		public Iterable<String> getCommandAttributes()
		{
			return commandAttributes;
		}

		@Override
		public Iterable<String[]> getAttributeGroups()
		{
			return attributeGroups;
		}

		@Override
		public PropertyViewAttribute<View> createPropertyViewAttribute(T defaultView, String propertyAttribute, String attributeValue)
		{
			return new MockPropertyViewAttribute(propertyAttribute);
		}

		@Override
		public AbstractCommandViewAttribute<View> createCommandViewAttribute(View defaultView, String commandAttribute, String attributeValue)
		{
			return new MockCommandViewAttribute(commandAttribute);
		}

		@Override
		public AbstractGroupedViewAttribute<View> createGroupedViewAttribute(View defaultView, String[] attributeGroup,
				Map<String, String> presentAttributeMappings)
		{
			return new MockGroupedViewAttribute(attributeGroup);
		}

	}

	private static class MockViewPendingAttribute implements PendingAttributesForView
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
		public void assertAllResolved()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEmpty()
		{
			throw new UnsupportedOperationException();
		}
	}

	private static class MockPropertyViewAttribute implements PropertyViewAttribute<View>
	{
		private String attributeName;

		public MockPropertyViewAttribute(String attributeName)
		{
			this.attributeName = attributeName;
		}

		@Override
		public int hashCode()
		{
			return attributeName.hashCode();
		}

		@Override
		public boolean equals(Object other)
		{
			if (this == other)
				return true;
			if (!(other instanceof MockPropertyViewAttribute))
				return false;

			final MockPropertyViewAttribute that = (MockPropertyViewAttribute) other;
			return new EqualsBuilder().append(attributeName, that.attributeName).isEquals();
		}

		@Override
		public void bindTo(BindingContext bindingContext)
		{
		}

		@Override
		public void setView(View view)
		{
		}

		@Override
		public void setAttributeValue(String attributeValue)
		{
		}

	}

	private static class MockCommandViewAttribute extends AbstractCommandViewAttribute<View>
	{
		private String attributeName;

		public MockCommandViewAttribute(String attributeName)
		{
			this.attributeName = attributeName;
		}

		@Override
		public int hashCode()
		{
			return attributeName.hashCode();
		}

		@Override
		public boolean equals(Object other)
		{
			if (this == other)
				return true;
			if (!(other instanceof MockCommandViewAttribute))
				return false;

			final MockCommandViewAttribute that = (MockCommandViewAttribute) other;
			return new EqualsBuilder().append(attributeName, that.attributeName).isEquals();
		}

		@Override
		protected Class<?> getPreferredCommandParameterType()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		protected void bind(Command command)
		{
		}
	}

	private static class MockGroupedViewAttribute extends AbstractGroupedViewAttribute<View>
	{
		private String[] attributeGroup;

		public MockGroupedViewAttribute(String[] attributeGroup)
		{
			this.attributeGroup = attributeGroup;
		}

		@Override
		public int hashCode()
		{
			return attributeGroup.hashCode();
		}

		@Override
		public boolean equals(Object other)
		{
			if (this == other)
				return true;
			if (!(other instanceof MockGroupedViewAttribute))
				return false;

			final MockGroupedViewAttribute that = (MockGroupedViewAttribute) other;
			return new EqualsBuilder().append(attributeGroup, that.attributeGroup).isEquals();
		}
		
		@Override
		public void bindTo(BindingContext bindingContext)
		{
		}
	}
}
