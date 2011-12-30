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

import org.junit.runner.RunWith;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.textview.TextAttributeGroup;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class StringTextAttributeTest extends AbstractTextAttributeTest<String>
{
	@Override
	protected AbstractPropertyViewAttribute<String> newAttributeInstance(String bindingAttributeValue)
	{
		TextAttributeGroup textAttribute = newTextAttribute(bindingAttributeValue);
		return textAttribute.new StringTextAttribute();
	}

	@Override
	protected void updateViewState(String newValue)
	{
		textView.setText(newValue);
	}

	@Override
	protected String getViewState()
	{
		return textView.getText().toString();
	}

}
