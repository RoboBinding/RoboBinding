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

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.beans.PresentationModelAdapterImpl;
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
	private MockPresentationModel mockPresentationModel;
	private View view;
	private Context context = new Activity();
	
	@Before
	public void setUp()
	{
		mockPresentationModel = new MockPresentationModel();
		view = new View(null);
	}
	
	@Test
	public void whenClickingOnTheView_ShouldInvokeCommandOnThePresentationModel()
	{
		String commandName = "someCommand";
		OnClickAttribute onClickAttribute = new OnClickAttribute(view, commandName);
		onClickAttribute.bind(new PresentationModelAdapterImpl(mockPresentationModel), context);
		
		view.performClick();
	
		assertTrue(mockPresentationModel.commandInvoked);
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
