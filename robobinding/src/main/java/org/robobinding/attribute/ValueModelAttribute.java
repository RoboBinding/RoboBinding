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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ValueModelAttribute extends AbstractPropertyAttribute
{
	private final static Pattern PROPERTY_ATTRIBUTE_PATTERN = Pattern.compile("[$]?\\{[\\w]+\\}");
	private final static Pattern PROPERTY_NAME_PATTERN = Pattern.compile("\\w+");
	
	private String propertyName;
	private boolean twoWayBinding;
	
	ValueModelAttribute(String name, String value)
	{
		super(name);
		determinePropertyName(value);
		determineBindingType(value);
	}
	
	private void determinePropertyName(String value)
	{
		Matcher matcher = PROPERTY_NAME_PATTERN.matcher(value);
		matcher.find();
		propertyName = matcher.group();
	}
	
	private void determineBindingType(String value)
	{
		twoWayBinding = value.startsWith("$");
	}
	
	public String getPropertyName()
	{
		return propertyName;
	}

	@Override
	public boolean isTwoWayBinding()
	{
		return twoWayBinding;
	}
	
	static boolean is(String value)
	{
		Matcher matcher = PROPERTY_ATTRIBUTE_PATTERN.matcher(value);
		return matcher.matches();
	}
}
