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

import robobinding.binding.BindingAttributeResolver;
import robobinding.binding.viewattribute.PropertyBindingDetails;
import robobinding.binding.viewattribute.TextAttribute;
import robobinding.binding.viewattribute.ValueCommitMode;
import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextViewAttributeProvider implements BindingAttributeProvider<TextView>
{
	private static final String TEXT = "text";
	private static final String VALUE_COMMIT_MODE = "valueCommitMode";
	private static final String[] TEXT_ATTRIBUTE_NAMES = {TEXT, VALUE_COMMIT_MODE};
	@Override
	public void resolveSupportedBindingAttributes(TextView textView, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeView)
	{
		if(bindingAttributeResolver.hasOneOfAttributes(TEXT_ATTRIBUTE_NAMES))
		{
			TextAttributeBuilder textAttributeBuilder = new TextAttributeBuilder(preInitializeView);
			textAttributeBuilder.setTextAttributeValue(bindingAttributeResolver.findAttributeValue(TEXT));
			textAttributeBuilder.setValueCommitModeAttributeValue(bindingAttributeResolver.findAttributeValue(VALUE_COMMIT_MODE));
			bindingAttributeResolver.resolveAttributes(TEXT_ATTRIBUTE_NAMES, textAttributeBuilder.create(textView));
		}
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
		protected boolean valueCommitModeSpecified()
		{
			return valueCommitModeAttributeValue != null;
		}
		
		TextAttribute create(TextView textView)
		{
			PropertyBindingDetails propertyBindingDetails = PropertyBindingDetails.createFrom(textAttributeValue, preInitializeView);
			
			if (!propertyBindingDetails.twoWayBinding && valueCommitModeSpecified())
				throw new RuntimeException("The valueCommitMode attribute can only be used when a two-way binding text attribute is specified");
			
			ValueCommitMode valueCommitMode = null;
			
			if (!valueCommitModeSpecified() || "onChange".equals(valueCommitModeAttributeValue))
				valueCommitMode = ValueCommitMode.ON_CHANGE;
			else if ("onFocusLost".equals(valueCommitModeAttributeValue))
				valueCommitMode = ValueCommitMode.ON_FOCUS_LOST;
			
			return new TextAttribute(textView, propertyBindingDetails, valueCommitMode);
		}
	}
}
