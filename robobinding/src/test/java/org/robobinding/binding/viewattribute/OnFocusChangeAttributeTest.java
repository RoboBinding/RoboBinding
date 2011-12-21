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

import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class OnFocusChangeAttributeTest extends AbstractViewCommandAttributeTest
{
	@Test
	public void givenBoundOnFocusAttribute_whenApplyFocus_thenEventReceived()
	{
		OnFocusChangeAttribute attribute = OnFocusChangeAttribute.createOnFocus(view, commandName);
		commandAttributeTester.bindAttribute(attribute);

		setViewFocus();

		assertEventReceived();
	}

	@Test
	public void givenBoundOnFocusLostAttribute_whenClearFocus_thenEventReceived()
	{
		OnFocusChangeAttribute attribute = OnFocusChangeAttribute.createOnFocusLost(view, commandName);
		commandAttributeTester.bindAttribute(attribute);

		clearViewFocus();

		assertEventReceived();
	}

	@Test
	public void givenBoundOnFocusChangeAttribute_whenApplyFocus_thenEventReceived()
	{
		OnFocusChangeAttribute attribute = OnFocusChangeAttribute.createOnFocusChange(view, commandName);
		commandAttributeTester.bindAttribute(attribute);

		setViewFocus();

		assertEventReceived();
	}

	@Test
	public void givenBoundOnFocusChangeAttribute_whenClearFocus_thenEventReceived()
	{
		OnFocusChangeAttribute attribute = OnFocusChangeAttribute.createOnFocusChange(view, commandName);
		commandAttributeTester.bindAttribute(attribute);

		clearViewFocus();

		assertEventReceived();
	}

	private void setViewFocus()
	{
		shadowView.setViewFocus(true);
	}

	private void clearViewFocus()
	{
		shadowView.setViewFocus(false);
	}

	private void assertEventReceived()
	{
		commandAttributeTester.assertEventReceived(ViewEvent.class);
	}
}
