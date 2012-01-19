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
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.GroupedAttributeDetails;
import org.robobinding.viewattribute.MockPresentationModelForProperty;
import org.robobinding.viewattribute.ViewAttribute;

import android.widget.SeekBar;


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

	@Before
	public void setUp()
	{
		onSeekBarAttributes = new OnSeekBarChangeAttributes();
		onSeekBarAttributes.setView(mock(SeekBar.class));
		mockGroupedAttributeDetails = mock(GroupedAttributeDetails.class);
		onSeekBarAttributes.setGroupedAttributeDetails(mockGroupedAttributeDetails);
	}
	
	@Test
	public void givenProgressAttributeValue_thenInitializeProgressAttributeInstance()
	{
		hasPropertyAttribute("progress");
		
		onSeekBarAttributes.postInitialization();
		
		assertThatAttributeWasCreated(TwoWayProgressAttribute.class);
	}

	@Test
	public void givenOnSeekBarChangeAttributeValue_thenInitializeOnSeekBarChangeAttributeInstance()
	{
		hasCommandAttribute("onSeekBarChange");
		
		onSeekBarAttributes.postInitialization();
		
		assertThatAttributeWasCreated(OnSeekBarChangeAttribute.class);
	}
	
	@Test
	public void givenProgressAndOnSeekBarChangeAttributeValues_thenInitializeBothAttributeInstances()
	{
		hasPropertyAttribute("progress");
		hasCommandAttribute("onSeekBarChange");
		
		onSeekBarAttributes.postInitialization();
		
		assertThatAttributeWasCreated(TwoWayProgressAttribute.class);
		assertThatAttributeWasCreated(OnSeekBarChangeAttribute.class);
	}

	private void hasPropertyAttribute(String attributeName)
	{
		when(mockGroupedAttributeDetails.hasAttribute(attributeName)).thenReturn(true);
		when(mockGroupedAttributeDetails.attributeValueFor(attributeName)).thenReturn(MockPresentationModelForProperty.ONE_WAY_BINDING_PROPERTY_NAME);
	}
	
	private void hasCommandAttribute(String commandName)
	{
		when(mockGroupedAttributeDetails.hasAttribute(commandName)).thenReturn(true);
		when(mockGroupedAttributeDetails.attributeValueFor(commandName)).thenReturn("some_method");
	}
	
	private void assertThatAttributeWasCreated(Class<? extends ViewAttribute> viewAttributeClass)
	{
		List<ViewAttribute> viewAttributes = onSeekBarAttributes.viewAttributes;
		boolean instanceFound = false;
		
		for (ViewAttribute viewAttribute : viewAttributes)
		{
			if (viewAttribute.getClass().isAssignableFrom(viewAttributeClass))
			{
				instanceFound = true;
				
				if (viewAttribute instanceof AbstractPropertyViewAttribute)
				{
					AbstractPropertyViewAttribute<?, ?> propertyViewAttribute = (AbstractPropertyViewAttribute<?, ?>)viewAttribute;
					assertTrue(propertyViewAttribute.getValidationError(), propertyViewAttribute.validate());
				}
				else if (viewAttribute instanceof AbstractCommandViewAttribute)
				{
					AbstractCommandViewAttribute<?> commandViewAttribute = (AbstractCommandViewAttribute<?>)viewAttribute;
					assertTrue(commandViewAttribute.getValidationError(), commandViewAttribute.validate());
				}
				
				break;
			}
		}
		
		assertTrue(instanceFound);
	}
	
}
