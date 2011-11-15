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
public class BindingDetailsBuilder
{
	private final static Pattern DYNAMIC_ATTRIBUTE_PATTERN = Pattern.compile("[$]?\\{[\\w]+\\}");
	private final static Pattern RESOURCE_ATTRIBUTE_PATTERN = Pattern.compile("^@((\\w+)/\\w+$)");
	private final static Pattern PROPERTY_NAME_PATTERN = Pattern.compile("\\w+");
	
	private final String attributeValue;
	private String propertyName;
	private boolean twoWayBinding;
	private boolean preInitializeView;
	private String resourceName;
	private String resourceType;
	
	public BindingDetailsBuilder(String attributeValue, boolean preInitializeView)
	{
		this.attributeValue = attributeValue;
		this.preInitializeView = preInitializeView;
		deriveProperties();
	}

	public PropertyBindingDetails createPropertyBindingDetails()
	{
		if (propertyName == null)
			throw new RuntimeException("Attribute value: " + attributeValue + " is not valid property attribute syntax.");
		
		return new PropertyBindingDetails(propertyName, twoWayBinding, preInitializeView);
	}
	
	public ResourceBindingDetails createResourceBindingDetails()
	{
		if (resourceName == null || resourceType == null)
			throw new RuntimeException("Attribute value: " + attributeValue + " is not valid resource attribute syntax.");
		
		return new ResourceBindingDetails(resourceName, resourceType);
	}
	
	private void deriveProperties()
	{
		boolean propertiesDetermined = false;
		Matcher matcher = DYNAMIC_ATTRIBUTE_PATTERN.matcher(attributeValue);
		
		if (matcher.matches())
		{
			determinePropertyName();
			determineBindingType();
			propertiesDetermined = true;
		}
		else
		{
			matcher = RESOURCE_ATTRIBUTE_PATTERN.matcher(attributeValue);
			
			if (matcher.matches())
			{
				determineResourceNameAndType(matcher);
				propertiesDetermined = true;
			}
		}
		
		if (!propertiesDetermined)
			throw new RuntimeException("Invalid binding property attribute value: '" + attributeValue + "'.\n\nDid you mean '{" + 
					attributeValue + "}' or '${" + attributeValue + "}'? (one/two-way binding)\n");
	}
	
	private void determinePropertyName()
	{
		Matcher matcher = PROPERTY_NAME_PATTERN.matcher(attributeValue);
		matcher.find();
		propertyName = matcher.group();
	}
	
	private void determineResourceNameAndType(Matcher matcher)
	{
		matcher.find();
		if (!matcher.matches() || matcher.groupCount() != 2)
			throw new RuntimeException("Invalid resource syntax: " + attributeValue);
		
		resourceName = matcher.group(1);
		resourceType = matcher.group(2);
	}
	
	public void determineBindingType()
	{
		twoWayBinding = attributeValue.startsWith("$");
	}

	public boolean bindsToStaticResource()
	{
		return resourceName != null;
	}
}
