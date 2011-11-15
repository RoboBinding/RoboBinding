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
import robobinding.binding.viewattribute.PropertyBindingDetails;
import robobinding.binding.viewattribute.TextAttribute;
import robobinding.binding.viewattribute.ValueCommitMode;
import robobinding.internal.com_google_common.collect.Lists;
import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextViewAttributeProvider implements BindingAttributeProvider<TextView>
{
	@Override
	public List<BindingAttribute> createSupportedBindingAttributes(TextView textView, Map<String, String> pendingBindingAttributes, boolean preInitializeView)
	{
		List<BindingAttribute> bindingAttributes = Lists.newArrayList();
		TextAttributeBuilder textAttributeBuilder = new TextAttributeBuilder(preInitializeView);
		
		for (String attributeName : pendingBindingAttributes.keySet())
		{
			String attributeValue = pendingBindingAttributes.get(attributeName);
			
			if ("text".equals(attributeName))
			{
				textAttributeBuilder.setTextAttributeValue(attributeValue);
			}
			else if ("valueCommitMode".equals(attributeName))
			{
				textAttributeBuilder.setValueCommitModeAttributeValue(attributeValue);
			}
		}
		
		if (textAttributeBuilder.hasValues())
			bindingAttributes.add(textAttributeBuilder.createBindingAttribute(textView));
		
		return bindingAttributes;
	}

	static class TextAttributeBuilder
	{
		private String textAttributeValue;
		private String valueCommitModeAttributeValue;
		private final boolean preInitializeView;
		
		public TextAttributeBuilder(boolean preInitializeView)
		{
			this.preInitializeView = preInitializeView;
		}
		void setTextAttributeValue(String textAttributeValue)
		{
			this.textAttributeValue = textAttributeValue;
		}
		void setValueCommitModeAttributeValue(String valueCommitModeAttributeValue)
		{
			this.valueCommitModeAttributeValue = valueCommitModeAttributeValue;
		}
		boolean hasValues()
		{
			return textAttributeValue != null || valueCommitModeSpecified();
		}
		protected boolean valueCommitModeSpecified()
		{
			return valueCommitModeAttributeValue != null;
		}
		
		BindingAttribute createBindingAttribute(TextView textView)
		{
			PropertyBindingDetails propertyBindingDetails = PropertyBindingDetails.createFrom(textAttributeValue, preInitializeView);
			
			if (!propertyBindingDetails.twoWayBinding && valueCommitModeSpecified())
				throw new RuntimeException("The valueCommitMode attribute can only be used when a two-way binding text attribute is specified");
			
			ValueCommitMode valueCommitMode = null;
			
			if (!valueCommitModeSpecified() || "onChange".equals(valueCommitModeAttributeValue))
				valueCommitMode = ValueCommitMode.ON_CHANGE;
			else if ("onFocusLost".equals(valueCommitModeAttributeValue))
				valueCommitMode = ValueCommitMode.ON_FOCUS_LOST;
			
			return new BindingAttribute(Lists.newArrayList("text", "valueCommitMode"), new TextAttribute(textView, propertyBindingDetails, valueCommitMode));
		}
	}
}
