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
package org.robobinding.binding.viewattribute;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.app.Activity;
import android.content.Context;
import android.widget.SeekBar;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class SeekBarAttributeTest
{
	private SeekBar seekBar;
	private Context context = new Activity();
	private MockFunction mockFunction;
	private PresentationModelAdapter mockPresentationModelAdapter;
	private final String commandName = "someCommand";
	
	@Before
	public void setUp()
	{
		seekBar = new SeekBar(null);
		mockFunction = new MockFunction();
		mockPresentationModelAdapter = mock(PresentationModelAdapter.class);
		when(mockPresentationModelAdapter.findFunction(commandName, SeekBarEvent.class)).thenReturn(mockFunction);
	}
	
	@Test
	public void whenSeekBarChanges_ThenInvokeCommand()
	{
		OnSeekBarChangeAttribute onClickAttribute = new OnSeekBarChangeAttribute(seekBar, commandName);
		onClickAttribute.bind(mockPresentationModelAdapter, context);
		
		seekBar.setProgress(75);
	
		assertTrue(mockFunction.commandInvoked);
	}
	
	@Test
	public void whenSeekBarChanges_ThenInvokeCommandWithSeekBarEvent()
	{
		OnSeekBarChangeAttribute onClickAttribute = new OnSeekBarChangeAttribute(seekBar, commandName);
		onClickAttribute.bind(mockPresentationModelAdapter, context);
		
		seekBar.setProgress(75);

		assertThat(mockFunction.argsPassedToInvoke[0], instanceOf(SeekBarEvent.class));
		SeekBarEvent seekBarEvent = (SeekBarEvent)mockFunction.argsPassedToInvoke[0];
		assertTrue(seekBarEvent.getSeekBar() == seekBar);
		assertThat(seekBarEvent.getProgress(), is(75));
		assertTrue(seekBarEvent.isFromUser());
	}
}
