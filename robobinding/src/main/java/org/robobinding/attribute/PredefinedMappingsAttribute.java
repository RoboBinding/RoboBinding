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
package org.robobinding.attribute;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.robobinding.PendingAttributesForView;
import org.robobinding.PendingAttributesForViewImpl;
import org.robobinding.PredefinedPendingAttributesForView;

import android.content.Context;
import android.view.View;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class PredefinedMappingsAttribute extends AbstractAttribute
{
	private static final String MAPPING_PATTERN = "(\\w+)\\.(\\w+):($?\\{\\w+\\})";
	private static final Pattern MAPPING_COMPILED_PATTERN = Pattern.compile(MAPPING_PATTERN);
	private static final Pattern MAPPING_ATTRIBUTE_COMPILED_PATTERN = Pattern.compile("^\\[" + MAPPING_PATTERN + "(?:," + MAPPING_PATTERN + ")*\\]$");
	private final String attributeValue;
	
	public PredefinedMappingsAttribute(String name, String value)
	{
		super(name);
		this.attributeValue = value;
		
		if (!MAPPING_ATTRIBUTE_COMPILED_PATTERN.matcher(value).matches())
			throw new MalformedAttributeException(getName(), "Mapping attribute value: " + value + " contains invalid syntax.");
	}

	public Collection<PredefinedPendingAttributesForView> getViewMappings(Context context)
	{
		return new ItemMappingParser().parse(getName(), attributeValue, context).getPredefinedPendingAttributesForViewGroup();
	}
	
	private static class ItemMappingParser
	{
		public ViewMappings parse(String name, String value, Context context)
		{
			Matcher matcher = MAPPING_COMPILED_PATTERN.matcher(value);
			ViewMappings viewMappings = new ViewMappings();

			while (matcher.find())
			{
				String viewIdString = matcher.group(1);
				String nestedAttributeName = matcher.group(2);
				String nestedAttributeValue = matcher.group(3);
				
				int viewId = context.getResources().getIdentifier(viewIdString, "id", "android");
				
				if (viewId == 0)
					throw new MalformedAttributeException(name, "View with id name: " + viewIdString + " in package: android could not be found");
				
				viewMappings.add(viewId, nestedAttributeName, nestedAttributeValue);
			}
			
			return viewMappings;
		}
	}
	
	private static class ViewMappings
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
		
		public Collection<PredefinedPendingAttributesForView> getPredefinedPendingAttributesForViewGroup()
		{
			return Lists.<PredefinedPendingAttributesForView>newArrayList(viewMappingsMap.values());
		}
	}
	
	static class ViewMapping implements PredefinedPendingAttributesForView
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

		@Override
		public PendingAttributesForView createPendingAttributesForView(View rootView)
		{
			View childView = rootView.findViewById(viewId);
			return new PendingAttributesForViewImpl(childView, bindingAttributes);
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

	public static PredefinedMappingsAttribute nullAttribute(String attributeName)
	{
		return new PredefinedMappingsAttribute(attributeName, "[text1.text:{property}]") {
			@Override
			public Collection<PredefinedPendingAttributesForView> getViewMappings(Context context)
			{
				return Lists.newArrayList();
			}
		};
	}
}
