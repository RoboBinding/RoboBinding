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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.beans.PresentationModelAdapter;
import robobinding.value.ValueHolders;
import robobinding.value.ValueModel;
import android.widget.TextView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@RunWith(RobolectricTestRunner.class)
public class StringTextAttributeTest
{
	private static final String INITIAL_VALUE = "initial value";
	private static final String NEW_VALUE = "new value";
	private static final String PROPERTY_NAME = "some_property";
	
	private TextView textView;
	private PresentationModelAdapter presentationModelAdapter;
	private ValueModel<String> valueModel;
	
	@Before
	public void setUp()
	{
		textView = new TextView(null);
		valueModel = ValueHolders.create(INITIAL_VALUE);
	}
	
	@Test
	public void whenBindingTextAttributeWith1WayOr2WayBinding_ThenTextViewShouldReflectValueModel()
	{
		stringTextAttributeWithEither1WayOr2WayBinding();
		
		assertThat(textView.getText().toString(), equalTo(INITIAL_VALUE));
	}

	@Test
	public void givenTextViewBoundWithEither1WayOr2WayBinding_WhenUpdatingValueModelText_ThenTextViewShouldBeUpdated()
	{
		stringTextAttributeWithEither1WayOr2WayBinding();
		
		valueModel.setValue(NEW_VALUE);
		
		assertThat(textView.getText().toString(), equalTo(NEW_VALUE));
	}
	
	@Test
	public void givenTextViewBoundWith1WayBinding_WhenUpdatingTextPropertyOnTheTextView_ThenValueModelShouldNotBeUpdated()
	{
		stringTextAttributeWith1WayBinding();
		
		textView.setText(NEW_VALUE);
		
		assertThat(valueModel.getValue(), equalTo(INITIAL_VALUE));
	}
	
	@Test
	public void givenTextViewBoundWith2WayBinding_WhenUpdatingTextPropertyOnTheTextView_ThenValueModelShouldBeUpdated()
	{
		stringTextAttributeWith2WayBinding();
		
		textView.setText(NEW_VALUE);
		
		assertThat(valueModel.getValue(), equalTo(NEW_VALUE));
	}

	private void stringTextAttributeWithEither1WayOr2WayBinding()
	{
		if (new Random().nextInt(2) == 0)
			stringTextAttributeWith1WayBinding();
		else
			stringTextAttributeWith2WayBinding();
	}
	
	private void stringTextAttributeWith1WayBinding()
	{
		mockPresentationModelFor1WayBinding();
		StringTextAttribute textAttribute = newStringTextAttribute();
		textAttribute.performOneWayBinding();
	}

	private void stringTextAttributeWith2WayBinding()
	{
		mockPresentationModelFor2WayBinding();
		StringTextAttribute textAttribute = newStringTextAttribute();
		textAttribute.performTwoWayBinding();
	}
	
	private void mockPresentationModelFor1WayBinding()
	{
		presentationModelAdapter = mock(PresentationModelAdapter.class);
		when(presentationModelAdapter.<String>getReadOnlyPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
	}
	
	private void mockPresentationModelFor2WayBinding()
	{
		presentationModelAdapter = mock(PresentationModelAdapter.class);
		when(presentationModelAdapter.<String>getPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
	}
	
	private StringTextAttribute newStringTextAttribute()
	{
		StringTextAttribute textAttribute = new StringTextAttribute(textView);
		textAttribute.setPresentationModelAdapter(presentationModelAdapter);
		textAttribute.setPropertyName(PROPERTY_NAME);
		return textAttribute;
	}
}
