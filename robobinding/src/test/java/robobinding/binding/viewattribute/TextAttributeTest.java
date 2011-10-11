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

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.binding.BindingType;
import robobinding.binding.viewattribute.TextAttribute;
import robobinding.value.ValueHolders;
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
public class TextAttributeTest
{
	private static final CharSequence INITIAL_VALUE = "initial value";
	private static final CharSequence NEW_VALUE = "new value";
	
	private TextView textView;
	private ValueModel<CharSequence> valueModel;
	
	@Before
	public void setUp()
	{
		textView = new TextView(new Activity());
		valueModel = ValueHolders.create(INITIAL_VALUE);
	}
	
	@Test
	public void givenAValueModelAndTextView_WhenInitializingTextViewConnectorWith1WayOr2WayBinding_ThenTextViewShouldReflectValueModel()
	{
		textConnectorWithEither1WayOr2WayBinding();
		
		assertThat(textView.getText(), equalTo(INITIAL_VALUE));
	}

	@Test
	public void givenAValueModelAndTextViewBoundWithEither1WayOr2WayBinding_WhenUpdatingValueModelText_ThenTextViewShouldBeUpdated()
	{
		textConnectorWithEither1WayOr2WayBinding();
		
		valueModel.setValue(NEW_VALUE);
		
		assertThat(textView.getText(), equalTo(NEW_VALUE));
	}
	
	@Test
	public void givenValueModelAndTextViewBoundWith1WayBinding_WhenUpdatingTextPropertyOnTheTextView_ThenValueModelShouldNotBeUpdated()
	{
		textConnectorWith1WayBinding();
		
		textView.setText(NEW_VALUE);
		
		assertThat(valueModel.getValue(), equalTo(INITIAL_VALUE));
	}
	
	@Test
	public void givenValueModelAndTextViewBoundWith2WayBinding_WhenUpdatingTextPropertyOnTheTextView_ThenValueModelShouldBeUpdated()
	{
		textConnectorWith2WayBinding();
		
		textView.setText(NEW_VALUE);
		
		assertThat(valueModel.getValue(), equalTo(NEW_VALUE));
	}

	private void textConnectorWith1WayBinding()
	{
		TextAttribute textViewConnector = new TextAttribute(textView);
		textViewConnector.bindOnto(valueModel,  BindingType.ONE_WAY);
	}
	
	private void textConnectorWith2WayBinding()
	{
		TextAttribute textViewConnector = new TextAttribute(textView);
		textViewConnector.bindOnto(valueModel,  BindingType.TWO_WAY);
	}
	
	private void textConnectorWithEither1WayOr2WayBinding()
	{
		if (new Random().nextInt(2) == 0)
			textConnectorWith1WayBinding();
		else
			textConnectorWith2WayBinding();
	}
}
