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
package org.robobinding.viewattribute;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.robobinding.MalformedAttributeException;

import android.content.Context;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ResourceAttributeValue implements AttributeValue
{
	private final static Pattern RESOURCE_ATTRIBUTE_PATTERN = Pattern.compile("^@([\\w\\.]+:)?(\\w+)/(\\w+)$");
	
	private String resourceName;
	private String resourceType;
	private String resourcePackage;
	
	public ResourceAttributeValue(String value)
	{
		determineResourceNameAndType(value);
	}
	
	private void determineResourceNameAndType(String value)
	{
		Matcher matcher = RESOURCE_ATTRIBUTE_PATTERN.matcher(value);
		
		matcher.find();
		if (!matcher.matches() || matcher.groupCount() < 2 || matcher.groupCount() > 3)
			throw new MalformedAttributeException("Invalid resource syntax: " + value);
		
		resourcePackage = matcher.group(1);
		
		if (resourcePackage != null && resourcePackage.length() > 0)
			resourcePackage = resourcePackage.substring(0, resourcePackage.length() - 1);
		
		resourceType = matcher.group(2);
		resourceName = matcher.group(3);
	}
	
	public int getResourceId(Context context)
	{
		return context.getResources().getIdentifier(resourceName, resourceType, resourcePackage == null ? context.getPackageName() : resourcePackage);
	}

	@Override
	public boolean isTwoWayBinding()
	{
		return false;
	}

	@Override
	public boolean isStaticResource()
	{
		return true;
	}
	
	@Override
	public ResourceAttributeValue asResourceAttributeValue()
	{
		return this;
	}
	
	@Override
	public PropertyAttributeValue asPropertyAttributeValue()
	{
		// TODO throws exception.
		return null;
	}

	public static boolean is(String value)
	{
		Matcher matcher = RESOURCE_ATTRIBUTE_PATTERN.matcher(value);
		
		return matcher.matches();
	}
}
