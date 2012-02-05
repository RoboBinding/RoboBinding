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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.robobinding.viewattribute.ViewAttribute;

import android.widget.SeekBar;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnSeekBarChangeAttributesTest extends AbstractGroupedViewAttributeTest<OnSeekBarChangeAttributes>
{
	private Attribute progress = attribute("progress={some_property}");
	private Attribute onSeekBarChange = attribute("onSeekBarChange=some_method");
	
	@Test
	public void givenProgressAttribute_thenInitializeProgressAttributeInstance()
	{
		givenAttribute(progress);
		
		performInitialization();
		
		assertThatAttributeWasCreated(TwoWayProgressAttribute.class);
	}

	@Test
	public void givenOnSeekBarChangeAttribute_thenInitializeOnSeekBarChangeAttributeInstance()
	{
		givenAttribute(onSeekBarChange);
		
		performInitialization();
		
		assertThatAttributeWasCreated(OnSeekBarChangeAttribute.class);
	}
	
	@Test
	public void givenProgressAndOnSeekBarChangeAttributes_thenInitializeBothAttributeInstances()
	{
		givenAttributes(progress, onSeekBarChange);
		
		performInitialization();
		
		assertThatAttributesWereCreated(TwoWayProgressAttribute.class, OnSeekBarChangeAttribute.class);
	}

	@Test
	public void whenInitializingAttributes_thenSetListenerOnTheView()
	{
		SeekBar mockSeekBar = mock(SeekBar.class);
		attributeUnderTest.setView(mockSeekBar);
		givenAttributes(progress, onSeekBarChange);
		
		performInitialization();
		
		verify(mockSeekBar).setOnSeekBarChangeListener(any(OnSeekBarChangeListeners.class));
	}
	
	@Override
	protected List<ViewAttribute> getGeneratedChildAttributes(OnSeekBarChangeAttributes attributeUnderTest)
	{
		return attributeUnderTest.viewAttributes;
	}
	
}
