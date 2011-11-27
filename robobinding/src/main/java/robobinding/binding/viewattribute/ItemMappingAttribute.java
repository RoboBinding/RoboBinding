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
package robobinding.binding.viewattribute;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import robobinding.binding.BindingAttributesReader;
import robobinding.binding.BindingAttributesReader.ViewBindingAttributes;
import robobinding.internal.com_google_common.collect.Lists;
import robobinding.internal.com_google_common.collect.Maps;
import robobinding.presentationmodel.DataSetAdapter;
import robobinding.presentationmodel.PresentationModelAdapter;
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
	private static final String ITEM_MAPPING_PATTERN = "(\\w+).(\\w+):($?{\\w+})";
	private static final String ITEM_MAPPING_ATTRIBUTE_PATTERN = "@\\[" + ITEM_MAPPING_PATTERN + "(," + ITEM_MAPPING_PATTERN + ")*\\]$";
	
	static class ItemMappingParser
	{

		public List<ViewMapping> parse(String attribute, Context context)
		{
			Pattern itemMappingPattern = Pattern.compile(ITEM_MAPPING_PATTERN);
			Matcher matcher = itemMappingPattern.matcher(attribute);
			List<ViewMapping> viewMappings = Lists.newArrayList();

			while (matcher.find())
			{
				Map<String, String> attribute = Maps.newHashMap();
				
				String viewIdString = matcher.group(1);
				int viewId = context.getResources().getIdentifier(viewIdString, "id", null);
				String attributeName = matcher.group(2);
				String attributeValue = matcher.group(3);
			}
			
			attribute.put("text", "{title}");
			viewMappings.add(new ViewMapping(10, attribute));
			return viewMappings;
		}

	}

	private List<ViewMapping> viewMappings;
	
	public ItemMappingAttribute(String itemMappingAttributeValue, boolean preInitializeView)
	{
		viewMappings = new ItemMappingParser().parse(itemMappingAttributeValue);
	}

	@Override
	public void bind(DataSetAdapter<?> dataSetAdapter, PresentationModelAdapter presentationModelAdapter, Context context)
	{
		dataSetAdapter.setItemMappingAttribute(this);
	}

	public void bind(View view, PresentationModelAdapter presentationModelAdapter, Context context)
	{
		for (ViewMapping viewMapping : viewMappings)
		{
			viewMapping.bind(view, presentationModelAdapter, context);
		}
	}
	
	static class ViewMapping
	{
		int viewId;
		Map<String,String> bindingAttributes;
		
		public ViewMapping(int viewId, Map<String, String> bindingAttributes)
		{
			this.viewId = viewId;
			this.bindingAttributes = bindingAttributes;
		}

		public void bind(View view, PresentationModelAdapter presentationModelAdapter, Context context)
		{
			View viewToBind = view.findViewById(viewId);
			BindingAttributesReader bindingAttributesReader = new BindingAttributesReader(null, false);
			ViewBindingAttributes viewBindingAttributes = bindingAttributesReader.read(viewToBind, bindingAttributes);
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
