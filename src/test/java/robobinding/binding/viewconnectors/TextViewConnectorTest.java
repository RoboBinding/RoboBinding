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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.value.ValueModel;
import android.app.Activity;
import android.widget.TextView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@RunWith(RobolectricTestRunner.class)
public class TextViewConnectorTest
{
	private static final CharSequence INITIAL_VALUE = "initial value";
	private static final CharSequence NEW_VALUE = "new value";
	
	private TextView textView;
	private ValueModel<CharSequence> valueModel;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp()
	{
		textView = new TextView(new Activity());
		valueModel = mock(ValueModel.class);
	}
	
	@Test
	public void givenAValueModelAndATextView_WhenInitializingTextViewConnector_ThenTextViewTextPropertyShouldReflectValueModel()
	{
		CharSequence initialValue = "initial value";
		when(valueModel.getValue()).thenReturn(initialValue);
		
		TextViewConnector textViewConnector = new TextViewConnector(valueModel, textView);
		assertThat(textView.getText(), equalTo(initialValue));
	}
	
	@Test
	public void givenABoundTextViewAndValueModel_WhenUpdatingTextPropertyOnTheTextView_ThenValueModelShouldBeUpdated()
	{
		when(valueModel.getValue()).thenReturn(INITIAL_VALUE);
		
		TextViewConnector textViewConnector = new TextViewConnector(valueModel, textView);
		textView.setText(NEW_VALUE);
		verify(valueModel).setValue(NEW_VALUE);
	}
}
