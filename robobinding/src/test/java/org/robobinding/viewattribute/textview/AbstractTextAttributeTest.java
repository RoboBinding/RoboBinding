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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.AbstractSingleTypeTwoWayPropertyAttributeTest;
import org.robobinding.viewattribute.PropertyBindingDetails;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowTextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractTextAttributeTest<T extends CharSequence> extends AbstractSingleTypeTwoWayPropertyAttributeTest<T>
{
	protected TextView textView;
	protected boolean lateValueCommitMode;
	
	@Before
	public void setUp()
	{
		textView = new TextView(null);
		lateValueCommitMode = false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void populateBindingExpectations(BindingSamples<T> bindingSamples)
	{
		bindingSamples.add((T)"initial value", (T)"new value");
	}
	
	protected TextAttribute newTextAttribute(String bindingAttributeValue)
	{
		return new TextAttribute(textView, PropertyBindingDetails.createFrom(bindingAttributeValue, true), lateValueCommitMode ? ValueCommitMode.ON_FOCUS_LOST : ValueCommitMode.ON_CHANGE);
	}
	
	@Test
	public void givenALateValueCommitAttribute_WhenUpdatingView_ThenDoNotImmediatelyCommitToValueModel()
	{
		lateValueCommitMode = true;
		createAttributeWith2WayBinding();
		String originalText = textView.getText().toString();
		
		textView.setText("some new text");
		
		assertThat(valueModel.getValue().toString(), equalTo(originalText));
	}
	
	@Test
	public void givenALateValueCommitAttribute_WhenViewLosesFocus_ThenCommitToValueModel()
	{
		lateValueCommitMode = true;
		createAttributeWith2WayBinding();
		String newText = "some new text";
		
		textView.setText(newText);
		ShadowTextView shadowTextView = Robolectric.shadowOf(textView);
		shadowTextView.setViewFocus(false);
		
		assertThat(valueModel.getValue().toString(), equalTo(newText));
	}
	
	@Test
	public void whenUpdatingTheTextView_ThenUpdateNotificationCountShouldBe1()
	{
		createAttributeWith2WayBinding();
		TextWatcherSpy textWatcher = new TextWatcherSpy();
		textView.addTextChangedListener(textWatcher);
		
		textView.setText("some new text");
		
		assertThat(textWatcher.updateNotificationCount, equalTo(1));
	}
	
	public class TextWatcherSpy implements TextWatcher
	{
		private int updateNotificationCount;
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			updateNotificationCount++;
		}

		@Override
		public void afterTextChanged(Editable s)
		{
		}

	}

}
