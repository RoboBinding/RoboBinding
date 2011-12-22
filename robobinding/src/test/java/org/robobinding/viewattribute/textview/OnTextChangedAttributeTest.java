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
package org.robobinding.viewattribute.textview;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.textview.OnTextChangedAttribute;
import org.robobinding.viewattribute.textview.TextChangedEvent;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class OnTextChangedAttributeTest extends AbstractTextViewCommandAttributeTest
{
	@Test
	public void givenBoundAttribute_whenChangeText_thenEventReceived()
	{
		OnTextChangedAttribute attribute = new OnTextChangedAttribute(textView, commandName);
		commandAttributeTester.bindAttribute(attribute);

		changeText();

		assertEventReceived();
	}

	private void changeText()
	{
		shadowTextView.setText(RandomStringUtils.random(5));
	}

	private void assertEventReceived()
	{
		commandAttributeTester.assertEventReceived(TextChangedEvent.class);
	}
}
