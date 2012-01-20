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
package org.robobinding.binder;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class GroupedAttributeDetailsImplTest
{
	private GroupedAttributeDetailsImpl groupedAttributeDetails;
	private String[] attributeNames;

	@Before
	public void setUp()
	{
		groupedAttributeDetails = new GroupedAttributeDetailsImpl(new String[0]);
		attributeNames = randomAttributeArray();
	}

	@Test
	public void givenNoAttributesHaveBeenAdded_whenFindingAbsentAttributes_thenReturnAllAttributes()
	{
		Collection<String> absentAttributes = groupedAttributeDetails.findAbsentAttributes(attributeNames);
	
		for (String attributeName : attributeNames)
			assertThat(absentAttributes, hasItem(attributeName));
	}
	
	@Test
	public void whenPresentAttributesHaveBeenAdded_thenShouldHaveAllAttributes()
	{
		for (int i = 0; i < attributeNames.length; i++)
			groupedAttributeDetails.addPresentAttribute(attributeNames[i], "attributeValue");
		
		assertTrue(groupedAttributeDetails.hasAttributes(attributeNames));
	}
	
	private String[] randomAttributeArray()
	{
		String[] attributeNames = new String[RandomValues.anyInteger()];
	
		for (int i = 0; i < attributeNames.length; i++)
			attributeNames[i] = RandomStringUtils.random(10);
		
		return attributeNames;
	}
}
