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

import robobinding.value.ValueModel;
import robobinding.value.experimental.ValueHolders;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@RunWith(RobolectricTestRunner.class)
public class IntegerVisibilityAttributeTest extends AbstractPropertyAttributeTest<Integer>
{
	private static final int INITIAL_VALUE = View.INVISIBLE;
	
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
				
		assertThat(view.getVisibility(), equalTo(View.INVISIBLE));
	}
	
	@Test
	public void givenBound_WhenIntegerPropertyIsSetToViewGone_ThenViewShouldBeGone()
	{
		createAttributeWith1WayBinding();
		
		updateValueModel(View.GONE);
		
		assertThat(view.getVisibility(), equalTo(View.GONE));
	}
	
	@Test
	public void givenBound_WhenIntegerPropertyIsSetToViewInvisible_ThenViewShouldBeInvisible()
	{
		createAttributeWith1WayBinding();
		
		updateValueModel(View.VISIBLE);
		updateValueModel(View.INVISIBLE);
		
		assertThat(view.getVisibility(), equalTo(View.INVISIBLE));
	}
	
	@Test
	public void givenBound_WhenIntegerPropertyIsSetToViewVisible_ThenViewShouldBeVisible()
	{
		createAttributeWith1WayBinding();
		
		updateValueModel(View.VISIBLE);
		
		assertThat(view.getVisibility(), equalTo(View.VISIBLE));
	}
	
	@Test(expected=RuntimeException.class)
	public void whenAttempting2WayBinding_ThenThrowARuntimeException()
	{
		createAttributeWith2WayBinding();
	}
	
	@Override
	protected AbstractPropertyViewAttribute<Integer> newAttributeInstance(String bindingAttributeValue)
	{
		VisibilityAttribute visibilityAttribute = new VisibilityAttribute(view, bindingAttributeValue);
		return visibilityAttribute.new IntegerVisibilityAttribute();
	}

	@Override
	protected ValueModel<Integer> initialValueModelInstance()
	{
		return ValueHolders.createInteger(INITIAL_VALUE);
	}
	
}
