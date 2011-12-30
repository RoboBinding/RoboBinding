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
package org.robobinding.viewattribute.adapterview;

import org.robobinding.viewattribute.AbstractBindingAttributeProvider;

import android.widget.AdapterView;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class AdapterViewAttributeProvider extends AbstractBindingAttributeProvider<AdapterView<?>>
{
	protected void populateViewAttributeMappings(ViewAttributeMappings<AdapterView<?>> mappings)
	{
		mappings.addGroupedMapping(AdaptedDataSetAttributes.class, "source", "itemLayout", "itemMapping", "dropdownLayout", "dropdownMapping");
		
		mappings.addCommandMapping("onItemClick", OnItemClickAttribute.class);
		mappings.addCommandMapping("onItemSelected", OnItemSelectedAttribute.class);
		
		mappings.addPropertyMapping("selectedItemPosition", SelectedItemPositionAttribute.class);
	}

//	public class AdapterViewAttributesBuilder
//	{
//		private final boolean preInitializeView;
//		private String sourceAttributeValue;
//		private String itemLayoutAttributeValue;
//		private String itemMappingAttributeValue;
//		private String dropdownLayoutAttributeValue;
//		private String dropdownMappingAttributeValue;
//		
//		public AdapterViewAttributesBuilder(boolean preInitializeView)
//		{
//			this.preInitializeView = preInitializeView;
//		}
//
//		public void setSourceAttributeValue(String attributeValue)
//		{
//			this.sourceAttributeValue = attributeValue;
//		}
//
//		public void setItemLayoutAttributeValue(String attributeValue)
//		{
//			this.itemLayoutAttributeValue = attributeValue;
//		}
//		
//		void setItemMappingAttributeValue(String itemMappingAttributeValue)
//		{
//			this.itemMappingAttributeValue = itemMappingAttributeValue;
//		}
//
//		public void setDropdownLayoutAttributeValue(String attributeValue)
//		{
//			this.dropdownLayoutAttributeValue = attributeValue;
//		}
//		
//		void setDropdownMappingAttributeValue(String dropdownMappingAttributeValue)
//		{
//			this.dropdownMappingAttributeValue = dropdownMappingAttributeValue;
//		}
//		
//		public AdaptedDataSetAttributes build(AdapterView<?> adapterView)
//		{
//			if (sourceAttributeValue == null || itemLayoutAttributeValue == null)
//				throw new RuntimeException("When binding to an AdapterView, both source and itemLayout attributes must be provided.");
//
//			if (adapterView instanceof AbsSpinner && dropdownLayoutAttributeValue == null)
//				throw new RuntimeException("When binding to an AbsSpinner, dropdownLayout attribute must be provided.");
//			
//			SourceAttribute sourceAttribute = new SourceAttribute(sourceAttributeValue, preInitializeView);
//			ItemLayoutAttribute itemLayoutAttribute = new ItemLayoutAttribute(itemLayoutAttributeValue, preInitializeView);
//			
//			DropdownLayoutAttribute dropdownLayoutAttribute = null;
//			ItemMappingAttribute itemMappingAttribute = null;
//			DropdownMappingAttribute dropdownMappingAttribute = null;
//			
//			if (dropdownLayoutAttributeValue != null)
//				dropdownLayoutAttribute = new DropdownLayoutAttribute(dropdownLayoutAttributeValue, preInitializeView);
//			
//			if (itemMappingAttributeValue != null)
//				itemMappingAttribute = new ItemMappingAttribute(itemMappingAttributeValue, preInitializeView);
//			
//			if (dropdownMappingAttributeValue != null)
//				dropdownMappingAttribute = new DropdownMappingAttribute(dropdownMappingAttributeValue, preInitializeView);
//			
//			return new AdaptedDataSetAttributes(adapterView, sourceAttribute, itemLayoutAttribute, itemMappingAttribute, dropdownLayoutAttribute, dropdownMappingAttribute);
//		}
//
//	}
}
