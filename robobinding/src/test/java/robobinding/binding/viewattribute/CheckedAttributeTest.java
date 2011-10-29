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
import robobinding.value.experimental.ValueHolders;
import android.widget.CheckBox;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class CheckedAttributeTest extends AbstractPropertyAttributeTest<Boolean>
{
	private static final boolean INITIAL_VALUE = true;
	private static final boolean NEW_VALUE = false;
	
	private CheckBox checkBox;
	
	@Before
	public void setUp()
	{
		checkBox = new CheckBox(null);
	}
	
	@Test
	public void whenBindingCheckedAttributeWith1WayOr2WayBinding_ThenCheckBoxShouldReflectValueModel()
	{
		createAttributeWithEither1WayOr2WayBinding();
		
		assertThat(checkBox.isChecked(), equalTo(INITIAL_VALUE));
	}

	@Test
	public void givenCheckBoxBoundWithEither1WayOr2WayBinding_WhenUpdatingValueModelCheckedState_ThenCheckBoxShouldBeUpdated()
	{
		createAttributeWithEither1WayOr2WayBinding();
		
		updateValueModel(NEW_VALUE);
		
		assertThat(checkBox.isChecked(), equalTo(NEW_VALUE));
	}
	
	@Test
	public void givenCheckBoxBoundWith1WayBinding_WhenUpdatingCheckedPropertyOnTheCheckBox_ThenValueModelShouldNotBeUpdated()
	{
		createAttributeWith1WayBinding();
		
		checkBox.setChecked(NEW_VALUE);
		
		assertThat(valueModel.getValue(), equalTo(INITIAL_VALUE));
	}
	
	@Test
	public void givenCheckBoxBoundWith2WayBinding_WhenUpdatingCheckedPropertyOnTheCheckBox_ThenValueModelShouldBeUpdated()
	{
		createAttributeWith2WayBinding();
		
		checkBox.setChecked(NEW_VALUE);
		
		assertThat(valueModel.getValue(), equalTo(NEW_VALUE));
	}
	
	@Override
	protected AbstractPropertyViewAttribute<Boolean> newAttributeInstance(String bindingAttributeValue)
	{
		return new CheckedAttribute(checkBox, bindingAttributeValue);
	}

	@Override
	protected PropertyValueModel<Boolean> initialValueModelInstance()
	{
		return ValueHolders.createBoolean(INITIAL_VALUE);
	}

}
