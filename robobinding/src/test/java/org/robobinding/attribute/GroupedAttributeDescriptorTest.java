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

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;

import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class GroupedAttributeDescriptorTest
{
	private PendingGroupAttributes groupedAttributeDescriptor;
	private String[] attributeNames;
	@Before
	public void setUp()
	{
		attributeNames = randomAttributeArray();
	}

	@Test
	public void givenAllAttributesArePresent_whenAssertingAllAttributesArePresent_thenDoNothing()
	{
		allAttributesArePresent();
		
		groupedAttributeDescriptor.assertAttributesArePresent(attributeNames);
	}
	
	@Test (expected = MissingRequiredAttributesException.class)
	public void givenNoAttributesArePresent_whenAssertingAllAttributesArePresent_thenThrowException()
	{
		noAttributeIsPresent();
		
		groupedAttributeDescriptor.assertAttributesArePresent(attributeNames);
	}
	
	private void noAttributeIsPresent()
	{
		groupedAttributeDescriptor = new PendingGroupAttributes(Maps.<String, String>newHashMap());
	}
	
	private void allAttributesArePresent()
	{
		Map<String, String> presentAttributeMappings = Maps.newHashMap();
		for(String attributeName : attributeNames)
		{
			presentAttributeMappings.put(attributeName, "attributeValue");
		}
		
		groupedAttributeDescriptor = new PendingGroupAttributes(presentAttributeMappings);
	}
	
	private String[] randomAttributeArray()
	{
		String[] attributeNames = new String[RandomValues.anyInteger() + 1];
	
		for (int i = 0; i < attributeNames.length; i++)
			attributeNames[i] = RandomStringUtils.random(10);
		
		return attributeNames;
	}
}
