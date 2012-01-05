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
package org.robobinding.viewattribute.textview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.textview.TextAttribute.CharSequenceTextAttribute;

import android.widget.TextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class CharSequenceAttributeTest extends AbstractPropertyViewAttributeTest<TextView, CharSequenceTextAttribute>
{
	@Test
	public void givenValueModelIsStringType_whenValueModelUpdated_thenViewShouldReflectChanges()
	{
		CharSequence newText = RandomStringUtils.random(5);
		
		attribute.valueModelUpdated(newText);

		assertThat(view.getText(), equalTo(newText));
	}
	
	@Test
	public void givenValueModelIsStringType_whenViewStateIsChanged_thenUpdateValueModel()
	{
		ValueModel<CharSequence> valueModel = twoWayBindToProperty(CharSequence.class);
		
		view.setText(RandomStringUtils.random(5));

		assertThat(valueModel.getValue(), equalTo(view.getText()));
	}
}
