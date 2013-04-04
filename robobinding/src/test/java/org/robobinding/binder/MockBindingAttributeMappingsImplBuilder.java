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

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.ChildViewAttributes;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;

import android.view.View;

import com.google.common.collect.Lists;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockBindingAttributeMappingsImplBuilder
{
	private List<String> propertyAttributes;
	private List<String> commandAttributes;
	private List<String[]> attributeGroups;

	private MockBindingAttributeMappingsImplBuilder()
	{
		propertyAttributes = Lists.newArrayList();
		commandAttributes = Lists.newArrayList();
		attributeGroups = Lists.newArrayList();
	}
	
	public static MockBindingAttributeMappingsImplBuilder aBindingAttributeMappingsImpl()
	{
		return new MockBindingAttributeMappingsImplBuilder();
	}
	
	public static PropertyViewAttribute<View> aPropertyViewAttribute(String attributeName)
	{
		return new MockPropertyViewAttribute(attributeName);
	}
	
	public static AbstractCommandViewAttribute<View> aCommandViewAttribute(String attributeName)
	{
		return new MockCommandViewAttribute(attributeName);
	}
	
	public static AbstractGroupedViewAttribute<View> aGroupedViewAttribute(String[] attributeGroup)
	{
		return new MockGroupedViewAttribute(attributeGroup);
	}
	
	public MockBindingAttributeMappingsImplBuilder withPropertyAttribute(String attribute)
	{
		propertyAttributes.add(attribute);
		return this;
	}
	
	public MockBindingAttributeMappingsImplBuilder withCommandAttribute(String attribute)
	{
		commandAttributes.add(attribute);
		return this;
	}
	
	public MockBindingAttributeMappingsImplBuilder withAttributeGroup(String[] attributeGroup)
	{
		attributeGroups.add(attributeGroup);
		return this;
	}
	
	public BindingAttributeMappingsImpl<View> build()
	{
		return new MockBindingAttributeMappingsImpl(this);
	}
	
	private static class MockBindingAttributeMappingsImpl extends BindingAttributeMappingsImpl<View>
	{
		private List<String> propertyAttributes;
		private List<String> commandAttributes;
		private List<String[]> attributeGroups;
		private MockBindingAttributeMappingsImpl(MockBindingAttributeMappingsImplBuilder builder)
		{
			super(null);
			propertyAttributes = Lists.newArrayList(builder.propertyAttributes);
			commandAttributes = Lists.newArrayList(builder.commandAttributes);
			attributeGroups = Lists.newArrayList(builder.attributeGroups);
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
		public PropertyViewAttribute<View> createPropertyViewAttribute(View defaultView, String propertyAttribute, String attributeValue)
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
		public void preInitializeView(BindingContext bindingContext)
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
		public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings)
		{
		}

		@Override
		protected void setupChildViewAttributes(ChildViewAttributes<View> childViewAttributes, BindingContext bindingContext)
		{
		}
	}

}
