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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class PropertyAttributeEvaluator
{
	private final static Pattern BINDING_ATTRIBUTE_PATTERN = Pattern.compile("[$]?\\{[\\w]+\\}");
	private final static Pattern PROPERTY_NAME_PATTERN = Pattern.compile("\\w+");
	
	private String attributeValue;	
	
	public PropertyAttributeEvaluator(String attributeValue)
	{
		this.attributeValue = attributeValue;
	}

	public void validate()
	{
		Matcher matcher = BINDING_ATTRIBUTE_PATTERN.matcher(attributeValue);
		
		if (!matcher.matches())
			throw new RuntimeException("Invalid binding property attribute value: '" + attributeValue + "'.\n\nDid you mean '{" + 
					attributeValue + "}' or '${" + attributeValue + "}'? (one/two-way binding respectively)\n");
	}
	
	public String determinePropertyName()
	{
		Matcher matcher = PROPERTY_NAME_PATTERN.matcher(attributeValue);
		matcher.find();
		return matcher.group();
	}
	
	public boolean isTwoWayBinding()
	{
		return attributeValue.startsWith("$");
	}
}
