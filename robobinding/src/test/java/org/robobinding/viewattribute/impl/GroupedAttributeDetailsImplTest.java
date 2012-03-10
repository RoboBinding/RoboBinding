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
package org.robobinding.viewattribute.impl;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.MissingRequiredBindingAttributeException;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.impl.GroupedAttributeDetailsImpl;

import com.google.common.collect.Maps;

import android.view.View;

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
	private View view;

	@Before
	public void setUp()
	{
		attributeNames = randomAttributeArray();
		view = mock(View.class);
	}

	@Test
	public void givenNoAttributesHaveBeenAdded_whenFindingAbsentAttributes_thenReturnAllAttributes()
	{
		noAttributeIsPresent();
		
		Collection<String> absentAttributes = groupedAttributeDetails.findAbsentAttributes(attributeNames);
	
		for (String attributeName : attributeNames)
			assertThat(absentAttributes, hasItem(attributeName));
	}

	@Test
	public void whenPresentAttributesHaveAllBeenAdded_thenShouldHaveAllAttributes()
	{
		allAttributesArePresent();
		
		assertTrue(groupedAttributeDetails.hasAttributes(attributeNames));
	}

	@Test
	public void givenAllAttributesArePresent_whenAssertingAllAttributesArePresent_thenDoNothing()
	{
		allAttributesArePresent();
		
		groupedAttributeDetails.assertAttributesArePresent(view, attributeNames);
	}
	
	@Test (expected = MissingRequiredBindingAttributeException.class)
	public void givenNoAttributesArePresent_whenAssertingAllAttributesArePresent_thenDoNothing()
	{
		noAttributeIsPresent();
		
		groupedAttributeDetails.assertAttributesArePresent(view, attributeNames);
	}
	
	private void noAttributeIsPresent()
	{
		groupedAttributeDetails = new GroupedAttributeDetailsImpl(Maps.<String, String>newHashMap());
	}
	
	private void allAttributesArePresent()
	{
		Map<String, String> presentAttributeMappings = Maps.newHashMap();
		for(String attributeName : attributeNames)
		{
			presentAttributeMappings.put(attributeName, "attributeValue");
		}
		
		groupedAttributeDetails = new GroupedAttributeDetailsImpl(presentAttributeMappings);
	}
	
	private String[] randomAttributeArray()
	{
		String[] attributeNames = new String[RandomValues.anyInteger() + 1];
	
		for (int i = 0; i < attributeNames.length; i++)
			attributeNames[i] = RandomStringUtils.random(10);
		
		return attributeNames;
	}
}
