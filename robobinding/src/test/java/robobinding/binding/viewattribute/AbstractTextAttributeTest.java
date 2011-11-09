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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import android.view.inputmethod.EditorInfo;
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
		return new TextAttribute(textView, new PropertyBinding(bindingAttributeValue), lateValueCommitMode ? ValueCommitMode.ON_FOCUS_LOST : ValueCommitMode.ON_CHANGE);
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
	public void givenALateValueCommitAttribute_WhenEditActionIsFinished_ThenCommitToValueModel()
	{
		lateValueCommitMode = true;
		createAttributeWith2WayBinding();
		String newText = "some new text";
		
		textView.setText(newText);
		ShadowTextView shadowTextView = Robolectric.shadowOf(textView);
		shadowTextView.triggerEditorAction(EditorInfo.IME_ACTION_DONE);
		
		assertThat(valueModel.getValue().toString(), equalTo(newText));
	}
}
