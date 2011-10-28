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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.beans.PresentationModelAdapter;
import robobinding.binding.viewattribute.VisibilityAttribute.IntegerVisibilityAttribute;
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
public class IntegerVisibilityAttributeTest
{
	private static final int INITIAL_VALUE = View.INVISIBLE;
	private static final String PROPERTY_NAME = "property_name";
	
	private View view;
	private PresentationModelAdapter presentationModelAdapter;
	private ValueModel<Integer> valueModel;
	
	@Before
	public void setUp()
	{
		view = new View(null);
		valueModel = ValueHolders.createInteger(INITIAL_VALUE);
	}
	
	@Test
	public void whenBinding_ThenViewShouldReflectModel()
	{
		mockPresentationModelFor1WayBinding();
		IntegerVisibilityAttribute visibilityAttribute = newIntegerVisibilityAttribute("{" + PROPERTY_NAME + "}");

		visibilityAttribute.performOneWayBinding();
				
		assertThat(view.getVisibility(), equalTo(View.INVISIBLE));
	}
	
	@Test
	public void givenBound_WhenIntegerPropertyIsSetToViewGone_ThenViewShouldBeGone()
	{
		mockPresentationModelFor1WayBinding();
		IntegerVisibilityAttribute visibilityAttribute = newIntegerVisibilityAttribute("{" + PROPERTY_NAME + "}");
		
		visibilityAttribute.performOneWayBinding();
		
		valueModel.setValue(View.GONE);
		
		assertThat(view.getVisibility(), equalTo(View.GONE));
	}
	
	@Test
	public void givenBound_WhenIntegerPropertyIsSetToViewInvisible_ThenViewShouldBeInvisible()
	{
		mockPresentationModelFor1WayBinding();
		IntegerVisibilityAttribute visibilityAttribute = newIntegerVisibilityAttribute("{" + PROPERTY_NAME + "}");
		visibilityAttribute.performOneWayBinding();
		
		valueModel.setValue(View.VISIBLE);
		valueModel.setValue(View.INVISIBLE);
		
		assertThat(view.getVisibility(), equalTo(View.INVISIBLE));
	}
	
	@Test
	public void givenBound_WhenIntegerPropertyIsSetToViewVisible_ThenViewShouldBeVisible()
	{
		mockPresentationModelFor1WayBinding();
		IntegerVisibilityAttribute visibilityAttribute = newIntegerVisibilityAttribute("{" + PROPERTY_NAME + "}");
		visibilityAttribute.performOneWayBinding();
		
		valueModel.setValue(View.VISIBLE);
		
		assertThat(view.getVisibility(), equalTo(View.VISIBLE));
	}
	
	@Test(expected=RuntimeException.class)
	public void whenAttempting2WayBinding_ThenThrowARuntimeException()
	{
		mockPresentationModelFor2WayBinding();
		IntegerVisibilityAttribute visibilityAttribute = newIntegerVisibilityAttribute("${" + PROPERTY_NAME + "}");
		visibilityAttribute.performTwoWayBinding();
	}
	
	private void mockPresentationModelFor1WayBinding()
	{
		presentationModelAdapter = mock(PresentationModelAdapter.class);
		when(presentationModelAdapter.<Integer>getReadOnlyPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
	}
	
	private void mockPresentationModelFor2WayBinding()
	{
		presentationModelAdapter = mock(PresentationModelAdapter.class);
		when(presentationModelAdapter.<Integer>getPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
	}
	
	private IntegerVisibilityAttribute newIntegerVisibilityAttribute(String bindingAttributeValue)
	{
		VisibilityAttribute visibilityAttribute = new VisibilityAttribute(view, bindingAttributeValue);
		IntegerVisibilityAttribute integerVisibilityAttribute = visibilityAttribute.new IntegerVisibilityAttribute();
		integerVisibilityAttribute.setPresentationModelAdapter(presentationModelAdapter);
		return integerVisibilityAttribute;
	}
	
}
