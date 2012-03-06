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
package org.robobinding.viewattribute;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.robobinding.binder.MalformedBindingAttributeException;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class BindingDetailsBuilder
{
	private final static Pattern DYNAMIC_ATTRIBUTE_PATTERN = Pattern.compile("[$]?\\{[\\w]+\\}");
	private final static Pattern RESOURCE_ATTRIBUTE_PATTERN = Pattern.compile("^@([\\w\\.]+:)?(\\w+)/(\\w+)$");
	private final static Pattern PROPERTY_NAME_PATTERN = Pattern.compile("\\w+");
	
	private final String attributeValue;
	
	private String propertyName;
	private boolean twoWayBinding;
	private String resourceName;
	private String resourceType;
	private String resourcePackage;
	
	public BindingDetailsBuilder(String attributeValue)
	{
		this.attributeValue = attributeValue;
		deriveProperties();
	}

	public PropertyBindingDetails createPropertyBindingDetails()
	{
		if (propertyName == null)
			throw new MalformedBindingAttributeException("Attribute value: " + attributeValue + " is not valid property attribute syntax.");
		
		return new PropertyBindingDetails(propertyName, twoWayBinding);
	}
	
	public ResourceBindingDetails createResourceBindingDetails()
	{
		if (resourceName == null || resourceType == null)
			throw new MalformedBindingAttributeException("Attribute value: " + attributeValue + " is not valid resource attribute syntax.");
		
		return new ResourceBindingDetails(resourceName, resourceType, resourcePackage);
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
			throw new MalformedBindingAttributeException(getErrorDescriptionForAttributeValue());
	}

	private String getErrorDescriptionForAttributeValue()
	{
		String errorMessage = "Invalid binding syntax: '" + attributeValue + "'.";
			
		Matcher propertyBindingAttempted = Pattern.compile("\\{.+|.+\\}").matcher(attributeValue);
		Matcher resourceBindingAttempted = Pattern.compile(".*@.*").matcher(attributeValue);
		
		if (!propertyBindingAttempted.matches())
			errorMessage += "\n\nDid you mean '{" + attributeValue + "}' or '${" + attributeValue + "}'? (one/two-way binding)\n";
		else if (resourceBindingAttempted.matches())
			errorMessage += "\n\nDid you mean to omit the curly braces? (Curly braces are for binding to a property on the presentation model, not static resources)";
		
		return errorMessage;
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
		if (!matcher.matches() || matcher.groupCount() < 2 || matcher.groupCount() > 3)
			throw new MalformedBindingAttributeException("Invalid resource syntax: " + attributeValue);
		
		resourcePackage = matcher.group(1);
		
		if (resourcePackage != null && resourcePackage.length() > 0)
			resourcePackage = resourcePackage.substring(0, resourcePackage.length() - 1);
		
		resourceType = matcher.group(2);
		resourceName = matcher.group(3);
	}
	
	public void determineBindingType()
	{
		twoWayBinding = attributeValue.startsWith("$");
	}

	public boolean bindsToStaticResource()
	{
		return resourceName != null;
	}

	String getPropertyName()
	{
		return propertyName;
	}

	boolean isTwoWayBinding()
	{
		return twoWayBinding;
	}

	String getResourceName()
	{
		return resourceName;
	}

	String getResourceType()
	{
		return resourceType;
	}

	String getResourcePackage()
	{
		return resourcePackage;
	}
}
