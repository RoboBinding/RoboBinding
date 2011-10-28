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

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.beans.PresentationModelAdapter;
import robobinding.value.ValueHolders;
import robobinding.value.ValueModel;
import android.app.Activity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.widget.TextView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
@SuppressWarnings({"rawtypes", "unchecked"})
@Ignore
public class TextAttributeTest
{
	private static final String PROPERTY_NAME = "some_property";
	
	private TextView textView;
	private ValueModel valueModel;
	private PresentationModelAdapter presentationModelAdapter;
	private PropertySample propertySample;
	
	@Test
	public void shouldBindWithCharSequenceProperties()
	{
		propertySample = new PropertySample<CharSequence>(CharSequence.class, "initial value", "new value");
		testBindingFeatures();
	}
	
	@Test
	public void shouldBindWithStringProperties()
	{
		propertySample = new PropertySample<String>(String.class, "initial value", "new value");
		testBindingFeatures();
	}
	
	@Test
	public void shouldBindWithSpannableStringProperties()
	{
		propertySample = new PropertySample<SpannableString>(SpannableString.class, new SpannableString("initial value"), new SpannableString("new value"));
		testBindingFeatures();
	}
	
	@Test
	public void shouldBindWithSpannedStringProperties()
	{
		propertySample = new PropertySample<SpannedString>(SpannedString.class, new SpannedString("initial value"), new SpannedString("new value"));
		testBindingFeatures();
	}
	
	@Test
	public void shouldBindWithSpannableStringBuilderProperties()
	{
		propertySample = new PropertySample<SpannableStringBuilder>(SpannableStringBuilder.class, new SpannableStringBuilder("initial value"), new SpannableStringBuilder("new value"));
		testBindingFeatures();
	}
	
	private void testBindingFeatures()
	{
		whenBindingTextAttributeWith1WayOr2WayBinding_ThenTextViewShouldReflectValueModel();
		givenTextViewBoundWithEither1WayOr2WayBinding_WhenUpdatingValueModelText_ThenTextViewShouldBeUpdated();
		givenTextViewBoundWith1WayBinding_WhenUpdatingTextPropertyOnTheTextView_ThenValueModelShouldNotBeUpdated();
		givenTextViewBoundWith2WayBinding_WhenUpdatingTextPropertyOnTheTextView_ThenValueModelShouldBeUpdated();
	}

	private void whenBindingTextAttributeWith1WayOr2WayBinding_ThenTextViewShouldReflectValueModel()
	{
		textAttributeWithEither1WayOr2WayBinding();
		
		assertThat(textView.getText().toString(), equalTo(String.valueOf(propertySample.initialValue)));
	}
	
	private void givenTextViewBoundWithEither1WayOr2WayBinding_WhenUpdatingValueModelText_ThenTextViewShouldBeUpdated()
	{
		textAttributeWithEither1WayOr2WayBinding();
		
		valueModel.setValue(propertySample.newValue);
		
		assertThat(textView.getText().toString(), equalTo(String.valueOf(propertySample.newValue)));
	}
	
	private void givenTextViewBoundWith1WayBinding_WhenUpdatingTextPropertyOnTheTextView_ThenValueModelShouldNotBeUpdated()
	{
		textAttributeWith1WayBinding();
		
		textView.setText(String.valueOf(propertySample.newValue));
		
		assertThat(valueModel.getValue().toString(), equalTo(String.valueOf(propertySample.initialValue)));
	}
	
	public void givenTextViewBoundWith2WayBinding_WhenUpdatingTextPropertyOnTheTextView_ThenValueModelShouldBeUpdated()
	{
		textAttributeWith2WayBinding();
		
		textView.setText(String.valueOf(propertySample.newValue));
		
		assertThat(valueModel.getValue().toString(), equalTo(String.valueOf(propertySample.newValue)));
	}
	
	private void textAttributeWithEither1WayOr2WayBinding()
	{
		if (new Random().nextInt(2) == 0)
			textAttributeWith1WayBinding();
		else
			textAttributeWith2WayBinding();
	}
	
	private void textAttributeWith1WayBinding()
	{
		valueModel = valueModelFor(propertySample.initialValue);
		mockPresentationModelFor1WayBinding();
		String attributeValue = "{" + PROPERTY_NAME + "}";
		bind(attributeValue);
	}
	
	private void textAttributeWith2WayBinding()
	{
		valueModel = valueModelFor(propertySample.initialValue);
		mockPresentationModelFor2WayBinding();
		String attributeValue = "${" + PROPERTY_NAME + "}";
		bind(attributeValue);
	}
	
	private void bind(String attributeValue)
	{
		textView = new TextView(null);
		
		TextAttribute textAttribute = new TextAttribute(textView, attributeValue);
		textAttribute.bind(presentationModelAdapter, new Activity());
	}
	
	private void mockPresentationModelFor1WayBinding()
	{
		presentationModelAdapter = mock(PresentationModelAdapter.class);
		when(presentationModelAdapter.getReadOnlyPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
		when(presentationModelAdapter.getPropertyType(PROPERTY_NAME)).thenReturn(propertySample.clazz);
	}
	
	private void mockPresentationModelFor2WayBinding()
	{
		presentationModelAdapter = mock(PresentationModelAdapter.class);
		when(presentationModelAdapter.getPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
		when(presentationModelAdapter.getPropertyType(PROPERTY_NAME)).thenReturn(propertySample.clazz);
	}
	
	private ValueModel valueModelFor(Object value)
	{
		if (value instanceof Integer)
		{
			return ValueHolders.createInteger((Integer)value);
		}
		return ValueHolders.create(value);
	}
	
	public static class PropertySample<T>
	{
		private final Class clazz;
		private final T initialValue;
		private final T newValue;

		public PropertySample(Class<T> clazz, T initialValue, T newValue)
		{
			this.clazz = clazz;
			this.initialValue = initialValue;
			this.newValue = newValue;
		}
	}
}
