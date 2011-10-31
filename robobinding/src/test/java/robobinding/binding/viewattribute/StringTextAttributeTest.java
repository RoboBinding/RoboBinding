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
import org.junit.runner.RunWith;

import robobinding.property.PropertyValueModel;
import android.widget.TextView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@RunWith(RobolectricTestRunner.class)
public class StringTextAttributeTest extends AbstractPropertyAttributeTest<String>
{
	private static final String INITIAL_VALUE = "initial value";
	private static final String NEW_VALUE = "new value";
	
	private TextView textView;
	
	@Before
	public void setUp()
	{
		textView = new TextView(null);
	}
	
	@Test
	public void whenBindingTextAttributeWith1WayOr2WayBinding_ThenTextViewShouldReflectValueModel()
	{
		createAttributeWithEither1WayOr2WayBinding();
		
		assertThat(textView.getText().toString(), equalTo(INITIAL_VALUE));
	}

	@Test
	public void givenTextViewBoundWithEither1WayOr2WayBinding_WhenUpdatingValueModelText_ThenTextViewShouldBeUpdated()
	{
		createAttributeWithEither1WayOr2WayBinding();
		
		updateValueModel(NEW_VALUE);
		
		assertThat(textView.getText().toString(), equalTo(NEW_VALUE));
	}
	
	@Test
	public void givenTextViewBoundWith1WayBinding_WhenUpdatingTextPropertyOnTheTextView_ThenValueModelShouldNotBeUpdated()
	{
		createAttributeWith1WayBinding();
		
		textView.setText(NEW_VALUE);
		
		assertThat(valueModel.getValue(), equalTo(INITIAL_VALUE));
	}
	
	@Test
	public void givenTextViewBoundWith2WayBinding_WhenUpdatingTextPropertyOnTheTextView_ThenValueModelShouldBeUpdated()
	{
		createAttributeWith2WayBinding();
		
		textView.setText(NEW_VALUE);
		
		assertThat(valueModel.getValue(), equalTo(NEW_VALUE));
	}

	@Override
	protected AbstractPropertyViewAttribute<String> newAttributeInstance(String bindingAttributeValue)
	{
		TextAttribute textAttribute = new TextAttribute(textView, bindingAttributeValue);
		return textAttribute.new StringTextAttribute();
	}

	@Override
	protected PropertyValueModel<String> initialValueModelInstance()
	{
		return ValueHolders.create(INITIAL_VALUE);
	}
}
