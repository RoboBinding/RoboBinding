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
package org.robobinding.binding.viewattribute;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.robobinding.binding.BindingAttributesProcessor;
import org.robobinding.binding.BindingAttributesProcessor.ViewBindingAttributes;
import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.presentationmodel.DataSetAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.content.Context;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ItemMappingAttribute implements AdapterViewAttribute
{
	private static final String ITEM_MAPPING_PATTERN = "(\\w+)\\.(\\w+):($?\\{\\w+\\})";
	private static final Pattern ITEM_MAPPING_COMPILED_PATTERN = Pattern.compile(ITEM_MAPPING_PATTERN);
	private static final Pattern ITEM_MAPPING_ATTRIBUTE_COMPILED_PATTERN = Pattern.compile("^\\[" + ITEM_MAPPING_PATTERN + "(?:," + ITEM_MAPPING_PATTERN + ")*\\]$");
	
	private final String itemMappingAttributeValue;
	private ViewMappings viewMappings;
	
	public ItemMappingAttribute(String itemMappingAttributeValue, boolean preInitializeView)
	{
		this.itemMappingAttributeValue = itemMappingAttributeValue;
	}

	@Override
	public void bind(DataSetAdapter<?> dataSetAdapter, PresentationModelAdapter presentationModelAdapter, Context context)
	{
		viewMappings = new ItemMappingParser().parse(itemMappingAttributeValue, context);
		updateDataSetAdapter(dataSetAdapter);
	}

	protected void updateDataSetAdapter(DataSetAdapter<?> dataSetAdapter)
	{
		dataSetAdapter.setItemMappingAttribute(this);
	}

	public void bindToPredefined(BindingAttributesProcessor bindingAttributesProcessor, View view, PresentationModelAdapter presentationModelAdapter, Context context)
	{
		for (ViewMapping viewMapping : getViewMappingsCollection())
		{
			viewMapping.bind(bindingAttributesProcessor, view, presentationModelAdapter, context);
		}
	}
	
	Collection<ViewMapping> getViewMappingsCollection()
	{
		return viewMappings.viewMappingsMap.values();
	}
	
	static class ItemMappingParser
	{
		public ViewMappings parse(String attribute, Context context)
		{
			if (!ITEM_MAPPING_ATTRIBUTE_COMPILED_PATTERN.matcher(attribute).matches())
				throw new RuntimeException("ItemMapping attribute value: " + attribute + " contains invalid syntax.");
			
			Matcher matcher = ITEM_MAPPING_COMPILED_PATTERN.matcher(attribute);
			ViewMappings viewMappings = new ViewMappings();

			while (matcher.find())
			{
				String viewIdString = matcher.group(1);
				String attributeName = matcher.group(2);
				String attributeValue = matcher.group(3);
				
				int viewId = context.getResources().getIdentifier(viewIdString, "id", "android");
				
				if (viewId == 0)
					throw new RuntimeException("View with id name: " + viewIdString + " in package: android could not be found");
				
				viewMappings.add(viewId, attributeName, attributeValue);
			}
			
			return viewMappings;
		}
	}
	
	static class ViewMappings
	{
		private Map<Integer, ViewMapping> viewMappingsMap = Maps.newHashMap();
		
		void add(int viewId, String attributeName, String attributeValue)
		{
			ViewMapping existingViewMapping = viewMappingsMap.get(viewId);
			
			if (existingViewMapping != null)
			{
				existingViewMapping.add(attributeName, attributeValue);
			}
			else
			{
				viewMappingsMap.put(viewId, new ViewMapping(viewId, attributeName, attributeValue));
			}
		}
		
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((viewMappingsMap == null) ? 0 : viewMappingsMap.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ViewMappings other = (ViewMappings) obj;
			if (viewMappingsMap == null)
			{
				if (other.viewMappingsMap != null)
					return false;
			} else if (!viewMappingsMap.equals(other.viewMappingsMap))
				return false;
			return true;
		}
	}
	
	static class ViewMapping
	{
		int viewId;
		Map<String,String> bindingAttributes = Maps.newHashMap();
		
		public ViewMapping(int viewId, String attributeName, String attributeValue)
		{
			this.viewId = viewId;
			this.add(attributeName, attributeValue);
		}

		public void add(String attributeName, String attributeValue)
		{
			this.bindingAttributes.put(attributeName, attributeValue);
		}

		public void bind(BindingAttributesProcessor bindingAttributesProcessor, View view, PresentationModelAdapter presentationModelAdapter, Context context)
		{
			View viewToBind = view.findViewById(viewId);
			ViewBindingAttributes viewBindingAttributes = bindingAttributesProcessor.process(viewToBind, Maps.newHashMap(bindingAttributes));
			viewBindingAttributes.bind(presentationModelAdapter, context);
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((bindingAttributes == null) ? 0 : bindingAttributes.hashCode());
			result = prime * result + viewId;
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ViewMapping other = (ViewMapping) obj;
			if (bindingAttributes == null)
			{
				if (other.bindingAttributes != null)
					return false;
			} else if (!bindingAttributes.equals(other.bindingAttributes))
				return false;
			if (viewId != other.viewId)
				return false;
			return true;
		}
	}
}
