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
import robobinding.binding.viewattribute.AdaptedDataSetAttributes;
import robobinding.binding.viewattribute.ItemLayoutAttribute;
import robobinding.binding.viewattribute.OnItemClickAttribute;
import robobinding.binding.viewattribute.SourceAttribute;
import robobinding.internal.com_google_common.collect.Lists;
import android.text.TextUtils;
import android.widget.ListView;


/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class ListViewAttributeProvider implements BindingAttributeProvider<ListView>
{
	@Override
	public List<BindingAttribute> createSupportedBindingAttributes(ListView listView, Map<String, String> pendingBindingAttributes, boolean autoInitializeView)
	{
		ListViewAttributesBuilder listViewAttributesBuilder = new ListViewAttributesBuilder(autoInitializeView);
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
			else if ("onItemClick".equals(attributeName))
			{
				bindingAttributes.add(new BindingAttribute(attributeName, new OnItemClickAttribute(listView, attributeValue)));
			}
		}
		
		if (listViewAttributesBuilder.hasAttributes())
			bindingAttributes.add(listViewAttributesBuilder.build(listView));
		
		return bindingAttributes;
	}
	
	public class ListViewAttributesBuilder
	{
		private final boolean preInitializeView;
		private String sourceAttributeValue;
		private String itemLayoutAttributeValue;
		private boolean hasAttributes = false;
		
		public ListViewAttributesBuilder(boolean preInitializeView)
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

		public BindingAttribute build(ListView listView)
		{
			if (TextUtils.isEmpty(sourceAttributeValue) || TextUtils.isEmpty(itemLayoutAttributeValue))
				throw new RuntimeException();

			SourceAttribute sourceAttribute = new SourceAttribute(sourceAttributeValue, preInitializeView);
			ItemLayoutAttribute itemLayoutAttribute = new ItemLayoutAttribute(itemLayoutAttributeValue, preInitializeView);
			AdaptedDataSetAttributes adaptedDataSetAttributes = new AdaptedDataSetAttributes(listView, sourceAttribute, itemLayoutAttribute);
			return new BindingAttribute(Lists.newArrayList("source", "itemLayout"), adaptedDataSetAttributes);
		}
	}
}
