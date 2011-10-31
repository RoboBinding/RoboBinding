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
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class EnabledAttributeTest extends AbstractPropertyAttributeTest<Boolean>
{
	private static final boolean INITIAL_VALUE = false;
	
	private View view;
	
	@Before
	public void setUp()
	{
		view = new View(null);
	}
	
	@Test
	public void whenBinding_ThenViewShouldReflectModel()
	{
		createAttributeWith1WayBinding();
		assertThat(view.isEnabled(), equalTo(INITIAL_VALUE));
	}
	
	@Test
	public void givenBound_WhenBooleanPropertyIsSetToFalse_ThenViewShouldDisabled()
	{
		createAttributeWith1WayBinding();
		
		updateValueModel(true);
		updateValueModel(false);
		
		assertThat(view.isEnabled(), equalTo(false));
	}
	
	@Test
	public void givenBound_WhenBooleanPropertyIsSetToTrue_ThenViewShouldBeEnabled()
	{
		createAttributeWith1WayBinding();
		
		updateValueModel(false);
		updateValueModel(true);
		
		assertThat(view.isEnabled(), equalTo(true));
	}
	
	@Test(expected=RuntimeException.class)
	public void whenAttempting2WayBinding_ThenThrowARuntimeException()
	{
		createAttributeWith2WayBinding();
	}
	
	@Override
	protected AbstractPropertyViewAttribute<Boolean> newAttributeInstance(String bindingAttributeValue)
	{
		return new EnabledAttribute(view, bindingAttributeValue);
	}

	@Override
	protected PropertyValueModel<Boolean> initialValueModelInstance()
	{
		return ValueHolders.createBoolean(INITIAL_VALUE);
	}
}
