/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package robobinding.binding;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import robobinding.binding.viewattribute.AbstractPropertyViewAttribute;
import robobinding.binding.viewattribute.DropdownLayoutAttribute;
import robobinding.binding.viewattribute.ItemLayoutAttribute;
import robobinding.binding.viewattribute.ListViewAttributes;
import robobinding.binding.viewattribute.SourceAttribute;
import robobinding.utils.Validate;
import android.widget.ListView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class ListViewAttributeProvider implements WidgetAttributeProvider<ListView>
{
	private List<AttributeGrouping> attributeGroupings;

	public ListViewAttributeProvider()
	{
		attributeGroupings = Lists.newArrayList();
		
		// Adding class, attributeValue
		// If these 'classes' have different implementations e.g. SourceAttribute -> ResourceSourceAttribute, PropertySourceAttribute
		// 		let that class handle it
		
		//this could work! probably need a viewattribute interface sooner rather than later
		
		add(newAttributeGrouping().withCompulsoryAttributes("source", "itemLayout").withOptionalAttributes("dropdownLayout"));
	}
	
	private void add(AttributeGroupingBuilder attributeGroupingBuilder)
	{
		attributeGroupings.add(attributeGroupingBuilder.build());
	}

	private AttributeGroupingBuilder newAttributeGrouping()
	{
		return new AttributeGroupingBuilder();
	}

	@Override
	public List<BindingAttribute> getSupportedBindingAttributes(ListView listView, Map<String, String> pendingBindingAttributes)
	{
		List<BindingAttribute> bindingAttributes = Lists.newArrayList();
		
		for (String attributeName : pendingBindingAttributes.keySet())
		{
			List<String> groupedAttributeNames = getGroupedAttributeNames(attributeName);
			
			if (groupedAttributeNames != null)
			{
				SourceAttribute sourceAttribute = new SourceAttribute(pendingBindingAttributes.get("source"));
				ItemLayoutAttribute itemLayoutAttribute = new ItemLayoutAttribute(pendingBindingAttributes.get("itemLayout"));
				DropdownLayoutAttribute dropdownLayoutAttribute = new DropdownLayoutAttribute(pendingBindingAttributes.get("dropdownLayout"));
				
				bindingAttributes.add(new GroupedBindingAttribute(groupedAttributeNames, new ListViewAttributes(listView, sourceAttribute, itemLayoutAttribute, dropdownLayoutAttribute)));
			}
		}
		
		return bindingAttributes;
	}

	private List<String> getGroupedAttributeNames(String attributeName)
	{
		for (AttributeGrouping grouping : attributeGroupings)
		{
			if (grouping.contains(attributeName))
			{
				return grouping.getAllAttributeNames();
			}
		}
		
		return null;
	}

	public class AttributeGrouping
	{
		private Map<Class<? extends PropertyViewAttribute>, String> compulsoryAttributes;
		private Map<Class<? extends PropertyViewAttribute>, String> optionalAttributes;

		public AttributeGrouping(Map<Class<? extends PropertyViewAttribute>, String> compulsoryAttributes, Map<Class<? extends PropertyViewAttribute>, String> optionalAttributes)
		{
			this.compulsoryAttributes = compulsoryAttributes;
			this.optionalAttributes = optionalAttributes;
		}

		public List<String> getAllAttributeNames()
		{
			List<String> attributes = Lists.newArrayList();
			
			for (Class<?> clazz : compulsoryAttributes.keySet())
				attributes.add(compulsoryAttributes.get(clazz));
			
			for (Class<?> clazz : optionalAttributes.keySet())
				attributes.add(optionalAttributes.get(clazz));
			
			return attributes;
		}
		
		public <T, VA extends AbstractPropertyViewAttribute<T>> AbstractPropertyViewAttribute<T> getPropertyViewAttribute(Class<VA> type, Map<String, String> bindingAttributes)
		{
			String attributeName = compulsoryAttributes.get(type);
			String attributeValue = bindingAttributes.get(attributeName);
			return type.getConstructor(String.class).newInstance(attributeValue);
		}

		public boolean contains(String attributeName)
		{
			return compulsoryAttributes.contains(attributeName) || optionalAttributes.contains(attributeName);
		}
	}
	
	public class AttributeGroupingBuilder
	{
		Map<Class<? extends PropertyViewAttribute>, String> compulsoryAttributes = Maps.newHashMap();
		Map<Class<? extends PropertyViewAttribute>, String> optionalAttributes = Maps.newHashMap();
		
		public AttributeGroupingBuilder addCompulsoryAttribute(Class<? extends PropertyViewAttribute> clazz, String attributeName)
		{
			compulsoryAttributes.put(clazz, attributeName);
			return this;
		}
		
		public AttributeGroupingBuilder withOptionalAttributes(Class<? extends PropertyViewAttribute> clazz, String attributeName)
		{
			optionalAttributes.put(clazz, attributeName);
			return this;
		}

		public AttributeGrouping build()
		{
			validate();
			return new AttributeGrouping(compulsoryAttributes, optionalAttributes);
		}

		private void validate()
		{
			Validate.notEmpty(compulsoryAttributes, "At least 1 compulsory attribute is required in an attribute grouping");
		}

	}
}
