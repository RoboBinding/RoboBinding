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
package robobinding.binding.viewattribute;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.beans.PresentationModelAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@RunWith(RobolectricTestRunner.class)
public class OnClickAttributeTest
{
	private View view;
	private Context context = new Activity();
	private MockCommand mockCommand;
	private PresentationModelAdapter mockPresentationModelAdapter;
	private final String commandName = "someCommand";
	
	@Before
	public void setUp()
	{
		view = new View(null);
		mockCommand = new MockCommand();
		mockPresentationModelAdapter = mock(PresentationModelAdapter.class);
		when(mockPresentationModelAdapter.findCommand(commandName)).thenReturn(mockCommand);
	}
	
	@Test
	public void whenClickingOnTheView_ThenInvokeCommand()
	{
		OnClickAttribute onClickAttribute = new OnClickAttribute(view, commandName);
		onClickAttribute.bind(mockPresentationModelAdapter, context);
		
		view.performClick();
	
		assertTrue(mockCommand.commandInvoked);
	}
	
	@Test
	public void whenClickingOnTheView_ThenInvokeCommandWithClickEvent()
	{
		OnClickAttribute onClickAttribute = new OnClickAttribute(view, commandName);
		onClickAttribute.bind(mockPresentationModelAdapter, context);
		
		view.performClick();
	
		assertThat(mockCommand.argsPassedToInvoke[0], instanceOf(ClickEvent.class));
		ClickEvent clickEvent = (ClickEvent)mockCommand.argsPassedToInvoke[0];
		assertTrue(clickEvent.getView() == view);
	}
}
