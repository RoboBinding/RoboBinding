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
package org.robobinding.viewattribute.edittext;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.robobinding.viewattribute.textview.CharSequenceMatcher.sameAs;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.edittext.TwoWayTextAttribute.TwoWayStringTextAttribute;

import android.widget.EditText;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowTextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayStringTextAttributeTest extends AbstractPropertyViewAttributeTest<EditText, TwoWayStringTextAttribute>
{
	@Test
	public void givenValueModelIsStringType_whenValueModelUpdated_thenViewShouldReflectChanges()
	{
		String newText = RandomStringUtils.randomAlphanumeric(5);
		
		attribute.valueModelUpdated(newText);

		assertThat(view.getText(), sameAs(newText));
	}
	
	@Test
	public void givenValueModelIsStringType_whenViewStateIsChanged_thenUpdateValueModel()
	{
		ValueModel<String> valueModel = twoWayBindToProperty(String.class);
		
		view.setText(RandomStringUtils.random(5));

		assertThat(valueModel.getValue(), sameAs(view.getText()));
	}
	
	@Test
	public void givenALateValueCommitAttribute_whenUpdatingView_thenDoNotImmediatelyCommitToValueModel()
	{
		attribute.setValueCommitMode(ValueCommitMode.ON_FOCUS_LOST);
		String newText = RandomStringUtils.randomAlphanumeric(5);
		ValueModel<String> valueModel = twoWayBindToProperty(String.class);
		
		view.setText(newText);

		assertThat(valueModel.getValue(), not(sameAs(newText)));
	}
	
	@Test
	public void givenALateValueCommitAttribute_whenViewLosesFocus_thenCommitToValueModel()
	{
		attribute.setValueCommitMode(ValueCommitMode.ON_FOCUS_LOST);
		String newText = RandomStringUtils.randomAlphanumeric(5);
		ValueModel<String> valueModel = twoWayBindToProperty(String.class);
		
		view.setText(newText);

		ShadowTextView shadowTextView = Robolectric.shadowOf(view);
		shadowTextView.setViewFocus(false);
		
		assertThat(valueModel.getValue(), sameAs(newText));
	}
}
