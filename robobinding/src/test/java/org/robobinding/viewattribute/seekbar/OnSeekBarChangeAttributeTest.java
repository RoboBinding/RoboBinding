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
package org.robobinding.viewattribute.seekbar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.view.AbstractCommandViewAttributeWithViewListenersAwareTest;

import android.widget.SeekBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnSeekBarChangeAttributeTest extends AbstractCommandViewAttributeWithViewListenersAwareTest<SeekBar, OnSeekBarChangeAttribute, MockSeekBarListeners>
{
	private int newProgressValue;
	
	@Before
	public void setUp()
	{
		newProgressValue = RandomValues.anyInteger();
	}
	
	@Test
	public void givenBoundAttribute_whenUpdatingProgress_thenEventReceived()
	{
		bindAttribute();

		updateProgressOnSeekBar();

		assertEventReceived();
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners()
	{
		bindAttribute();
		
		assertTrue(viewListeners.addOnSeekBarChangeListenerInvoked);
	}
	
	private void updateProgressOnSeekBar()
	{
		view.setProgress(newProgressValue);
	}

	private void assertEventReceived()
	{
		assertEventReceived(SeekBarEvent.class);
		SeekBarEvent seekBarEvent = getEventReceived();
		assertThat(seekBarEvent.getSeekBar(), sameInstance(view));
		assertThat(seekBarEvent.getProgress(), is(newProgressValue));
		assertTrue(seekBarEvent.isFromUser());
	}
}
