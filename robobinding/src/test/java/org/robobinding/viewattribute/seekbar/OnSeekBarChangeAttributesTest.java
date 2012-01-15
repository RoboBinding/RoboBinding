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
package org.robobinding.viewattribute.seekbar;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.GroupedAttributeDetails;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.ViewAttributeInjectorSpy;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnSeekBarChangeAttributesTest
{
	private OnSeekBarChangeAttributes onSeekBarAttributes;
	private GroupedAttributeDetails mockGroupedAttributeDetails;
	private ViewAttributeInjectorSpy viewAttributeInjectorSpy;

	@Before
	public void setUp()
	{
		onSeekBarAttributes = new OnSeekBarChangeAttributes();
		mockGroupedAttributeDetails = mock(GroupedAttributeDetails.class);
		onSeekBarAttributes.setGroupedAttributeDetails(mockGroupedAttributeDetails);
		viewAttributeInjectorSpy = new ViewAttributeInjectorSpy();
		onSeekBarAttributes.setViewAttributeInjector(viewAttributeInjectorSpy);
	}
	
	@Test
	public void givenProgressAttributeValue_thenInitializeProgressAttributeInstance()
	{
		when(mockGroupedAttributeDetails.hasAttribute("progress")).thenReturn(true);
		
		onSeekBarAttributes.initializeChildViewAttributes();
		
		assertThatAttributeWasCreated(TwoWayProgressAttribute.class);
	}
	
	@Test
	public void givenOnSeekBarChangeAttributeValue_thenInitializeOnSeekBarChangeAttributeInstance()
	{
		when(mockGroupedAttributeDetails.hasAttribute("onSeekBarChange")).thenReturn(true);
		
		onSeekBarAttributes.initializeChildViewAttributes();
		
		assertThatAttributeWasCreated(OnSeekBarChangeAttribute.class);
	}
	
	@Test
	public void givenProgressAndOnSeekBarChangeAttributeValues_thenInitializeBothAttributeInstances()
	{
		when(mockGroupedAttributeDetails.hasAttribute("progress")).thenReturn(true);
		when(mockGroupedAttributeDetails.hasAttribute("onSeekBarChange")).thenReturn(true);
		
		onSeekBarAttributes.initializeChildViewAttributes();
		
		assertThatAttributeWasCreated(TwoWayProgressAttribute.class);
		assertThatAttributeWasCreated(OnSeekBarChangeAttribute.class);
	}
	
	private void assertThatAttributeWasCreated(Class<? extends ViewAttribute> viewAttributeClass)
	{
		List<ViewAttribute> viewAttributes = onSeekBarAttributes.getViewAttributes();
		boolean instanceFound = false;
		
		for (ViewAttribute viewAttribute : viewAttributes)
		{
			if (viewAttribute.getClass().isAssignableFrom(viewAttributeClass))
			{
				instanceFound = true;
				break;
			}
		}
		
		assertTrue(instanceFound);
		assertTrue(viewAttributeInjectorSpy.viewAttributeValuesInjected(viewAttributeClass));
	}
	
}
