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
package org.robobinding.binder;

import java.util.Map;

import android.util.AttributeSet;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class BindingAttributeParser {
    public static final String ROBOBINDING_NAMESPACE = "http://robobinding.org/android";

    public Map<String, String> parse(AttributeSet attributeSet) {
	Map<String, String> bindingAttributes = Maps.newHashMap();

	for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
	    String attributeName = attributeSet.getAttributeName(i);
	    String attributeValue = attributeSet.getAttributeValue(ROBOBINDING_NAMESPACE, attributeName);

	    if (attributeValue != null)
		bindingAttributes.put(attributeName, attributeValue);
	}

	return bindingAttributes;
    }
}
