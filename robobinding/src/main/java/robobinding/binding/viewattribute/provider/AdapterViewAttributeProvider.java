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
package robobinding.binding.viewattribute.provider;

import java.util.List;
import java.util.Map;

import robobinding.binding.BindingAttribute;
import robobinding.binding.BindingAttributesReader;
import robobinding.binding.ProvidersResolver;
import robobinding.binding.viewattribute.AdaptedDataSetAttributes;
import robobinding.binding.viewattribute.DropdownLayoutAttribute;
import robobinding.binding.viewattribute.DropdownMappingAttribute;
import robobinding.binding.viewattribute.ItemLayoutAttribute;
import robobinding.binding.viewattribute.ItemMappingAttribute;
import robobinding.binding.viewattribute.OnItemClickAttribute;
import robobinding.binding.viewattribute.SourceAttribute;
import robobinding.internal.com_google_common.collect.Lists;
import android.widget.AdapterView;


/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class AdapterViewAttributeProvider implements BindingAttributeProvider<AdapterView<?>>
{
	@Override
	public List<BindingAttribute> createSupportedBindingAttributes(AdapterView<?> adapterView, Map<String, String> pendingBindingAttributes, boolean autoInitializeView)
	{
		AdapterViewAttributesBuilder listViewAttributesBuilder = new AdapterViewAttributesBuilder(autoInitializeView);
		List<BindingAttribute> bindingAttributes = Lists.newArrayList();
		
		for (String attributeName : pendingBindingAttributes.keySet())
		{
			String attributeValue = pendingBindingAttributes.get(attributeName);
			
			if ("source".equals(attributeName))
			{
				listViewAttributesBuilder.setSourceAttributeValue(attributeValue);
			}
			else if ("itemLayout".equals(attributeName))
			{
				listViewAttributesBuilder.setItemLayoutAttributeValue(attributeValue);
			}
			else if ("dropdownLayout".equals(attributeName))
			{
				listViewAttributesBuilder.setDropdownLayoutAttributeValue(attributeValue);
			}
			else if ("onItemClick".equals(attributeName))
			{
				bindingAttributes.add(new BindingAttribute(attributeName, new OnItemClickAttribute(adapterView, attributeValue)));
			}
		}
		
		if (listViewAttributesBuilder.hasAttributes())
			bindingAttributes.add(listViewAttributesBuilder.build(adapterView));
		
		return bindingAttributes;
	}
	
	public class AdapterViewAttributesBuilder
	{
		private final boolean preInitializeView;
		private String sourceAttributeValue;
		private String itemLayoutAttributeValue;
		private String itemMappingAttributeValue;
		private String dropdownLayoutAttributeValue;
		private String dropdownMappingAttributeValue;
		private boolean hasAttributes = false;
		
		public AdapterViewAttributesBuilder(boolean preInitializeView)
		{
			this.preInitializeView = preInitializeView;
		}

		public void setSourceAttributeValue(String attributeValue)
		{
			this.sourceAttributeValue = attributeValue;
			hasAttributes = true;
		}

		public boolean hasAttributes()
		{
			return hasAttributes;
		}

		public void setItemLayoutAttributeValue(String attributeValue)
		{
			this.itemLayoutAttributeValue = attributeValue;
			hasAttributes = true;
		}
		
		void setItemMappingAttributeValue(String itemMappingAttributeValue)
		{
			this.itemMappingAttributeValue = itemMappingAttributeValue;
			hasAttributes = true;
		}

		public void setDropdownLayoutAttributeValue(String attributeValue)
		{
			this.dropdownLayoutAttributeValue = attributeValue;
			hasAttributes = true;
		}
		
		void setDropdownMappingAttributeValue(String dropdownMappingAttributeValue)
		{
			this.dropdownMappingAttributeValue = dropdownMappingAttributeValue;
			hasAttributes = true;
		}
		
		public BindingAttribute build(AdapterView<?> adapterView)
		{
			if (sourceAttributeValue == null || itemLayoutAttributeValue == null)
				throw new RuntimeException();

			SourceAttribute sourceAttribute = new SourceAttribute(sourceAttributeValue, preInitializeView);
			ItemLayoutAttribute itemLayoutAttribute = new ItemLayoutAttribute(itemLayoutAttributeValue, preInitializeView);
			
			DropdownLayoutAttribute dropdownLayoutAttribute = null;
			ItemMappingAttribute itemMappingAttribute = null;
			DropdownMappingAttribute dropdownMappingAttribute = null;
			
			if (dropdownLayoutAttributeValue != null)
				dropdownLayoutAttribute = new DropdownLayoutAttribute(dropdownLayoutAttributeValue, preInitializeView);
			
			if (itemMappingAttributeValue != null)
				itemMappingAttribute = new ItemMappingAttribute(itemMappingAttributeValue, preInitializeView);
			
			if (dropdownMappingAttributeValue != null)
				dropdownMappingAttribute = new DropdownMappingAttribute(dropdownMappingAttributeValue, preInitializeView);
			
			AdaptedDataSetAttributes adaptedDataSetAttributes = new AdaptedDataSetAttributes(adapterView, sourceAttribute, itemLayoutAttribute, itemMappingAttribute, dropdownLayoutAttribute, dropdownMappingAttribute);
			return new BindingAttribute(Lists.newArrayList("source", "itemLayout", "dropdownLayout"), adaptedDataSetAttributes);
		}

	}
}
