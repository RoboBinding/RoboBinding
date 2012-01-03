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
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.MockPresentationModelForProperty;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowTextView;

import android.widget.TextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextAttributeTest extends AbstractPropertyViewAttributeTest<TextView, TextAttribute>
{
	@Before
	public void setUp()
	{
		attribute.setValueCommitMode(ValueCommitMode.ON_CHANGE);
	}
	
	@Test
	public void givenValueModelIsStringType_whenValueModelUpdated_thenViewShouldReflectChanges()
	{
		String newText = RandomStringUtils.random(5);
		MockPresentationModelForProperty<String> presentationModel = initializeForOneWayBinding(String.class);
		
		presentationModel.updatePropertyValue(newText);

		assertThat(view.getText().toString(), equalTo(newText));
	}
	
	@Test
	public void givenValueModelIsStringType_whenViewStateIsChanged_thenUpdateValueModel()
	{
		String newText = RandomStringUtils.random(5);
		MockPresentationModelForProperty<String> presentationModel = initializeForTwoWayBinding(String.class);
		
		view.setText(newText);

		assertThat(presentationModel.getPropertyValue(), equalTo(newText));
	}
	
	@Test
	public void givenValueModelIsCharSequenceType_whenValueModelUpdated_thenViewShouldReflectChanges()
	{
		CharSequence newText = RandomStringUtils.random(5);
		MockPresentationModelForProperty<CharSequence> presentationModel = initializeForOneWayBinding(CharSequence.class);
		
		presentationModel.updatePropertyValue(newText);

		assertThat(view.getText(), equalTo(newText));
	}
	
	@Test
	public void givenValueModelIsCharSequenceType_whenViewStateIsChanged_thenUpdateValueModel()
	{
		CharSequence newText = RandomStringUtils.random(5);
		MockPresentationModelForProperty<CharSequence> presentationModel = initializeForTwoWayBinding(CharSequence.class);
		
		view.setText(newText);

		assertThat(presentationModel.getPropertyValue().toString(), equalTo(newText));
	}
	
	@Test
	public void givenALateValueCommitAttribute_whenUpdatingView_thenDoNotImmediatelyCommitToValueModel()
	{
		attribute.setValueCommitMode(ValueCommitMode.ON_FOCUS_LOST);
		String newText = RandomStringUtils.random(5);
		MockPresentationModelForProperty<String> presentationModel = initializeForTwoWayBinding(String.class);
		
		view.setText(newText);

		assertThat(presentationModel.getPropertyValue(), not(equalTo(newText)));
	}
	
	@Test
	public void givenALateValueCommitAttribute_whenViewLosesFocus_thenCommitToValueModel()
	{
		attribute.setValueCommitMode(ValueCommitMode.ON_FOCUS_LOST);
		String newText = RandomStringUtils.random(5);
		MockPresentationModelForProperty<String> presentationModel = initializeForTwoWayBinding(String.class);
		
		view.setText(newText);

		ShadowTextView shadowTextView = Robolectric.shadowOf(view);
		shadowTextView.setViewFocus(false);
		
		assertThat(presentationModel.getPropertyValue(), equalTo(newText));
	}
}
