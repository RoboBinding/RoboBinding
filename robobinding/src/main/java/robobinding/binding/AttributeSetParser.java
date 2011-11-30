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

import java.util.Map;

import robobinding.internal.com_google_common.collect.Maps;

import android.util.AttributeSet;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class AttributeSetParser
{
	static final String ROBOBINDING_NAMESPACE = "http://robobinding.org/robobinding.android";

	Map<String, String> parse(AttributeSet attributeSet)
	{
		Map<String, String> bindingAttributes = Maps.newHashMap();
		
		for (int i = 0; i < attributeSet.getAttributeCount(); i++)
		{
			String attributeName = attributeSet.getAttributeName(i);
			String attributeValue = attributeSet.getAttributeValue(ROBOBINDING_NAMESPACE, attributeName);
			
			if (attributeValue != null)
				bindingAttributes.put(attributeName, attributeValue);
		}
		
		return bindingAttributes;
	}
}
