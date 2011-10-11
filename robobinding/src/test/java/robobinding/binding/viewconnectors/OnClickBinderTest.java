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
package robobinding.binding.viewconnectors;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import android.view.View;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@RunWith(RobolectricTestRunner.class)
public class OnClickBinderTest
{
	private MockPresentationModel mockPresentationModel;
	private View view;

	public void setUp()
	{
		mockPresentationModel = new MockPresentationModel();
		view = new View(null);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void givenAViewAPresentationModelAndACommandName_WhenClickingOnTheView_ShouldInvokeAMethodOnThePresentationModelWithTheGivenCommandName()
	{
		String commandName = "someCommand";
		OnClickBinder onClickBinder = new OnClickBinder(view, commandName, mockPresentationModel);

		view.performClick();
	
		assertTrue(mockPresentationModel.commandInvoked);
	}
	
	@SuppressWarnings("unused")
	@Test (expected=RuntimeException.class)
	public void whenInitializingWithAnInvalidCommandName_ShouldThrowARuntimeException()
	{
		String invalidCommandName = "invalidCommand";
		OnClickBinder onClickBinder = new OnClickBinder(view, invalidCommandName, mockPresentationModel);
	}
	
	private static class MockPresentationModel
	{
		private boolean commandInvoked;

		@SuppressWarnings("unused")
		public void someCommand()
		{
			commandInvoked = true;
		}
	}
}
