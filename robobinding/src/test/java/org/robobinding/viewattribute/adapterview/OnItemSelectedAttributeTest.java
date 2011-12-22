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
package org.robobinding.viewattribute.adapterview;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.MockFunction;
import org.robobinding.viewattribute.adapterview.ItemClickEvent;
import org.robobinding.viewattribute.adapterview.OnItemSelectedAttribute;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class OnItemSelectedAttributeTest
{
	private static final int POSITION_TO_SELECT = 1;
	
	private Spinner adapterView;
	private Context context = new Activity();
	private MockFunction mockFunction;
	private PresentationModelAdapter mockPresentationModelAdapter;
	private final String commandName = "someCommand";
	private ArrayAdapter<String> arrayAdapter;
	
	@Before
	public void setUp()
	{
		adapterView = new Spinner(context);
		mockFunction = new MockFunction();
		mockPresentationModelAdapter = mock(PresentationModelAdapter.class);
		when(mockPresentationModelAdapter.findFunction(commandName, ItemClickEvent.class)).thenReturn(mockFunction);
		
		arrayAdapter = new MockArrayAdapter(new Activity(), R.layout.simple_list_item_1, Lists.newArrayList("0", "1", "2", "3", "4", "5"));
		adapterView.setAdapter(arrayAdapter);
	}
	
	@Test
	public void whenSelectingAnItem_ThenInvokeCommand()
	{
		OnItemSelectedAttribute onItemSelectedAttribute = new OnItemSelectedAttribute(adapterView, commandName);
		onItemSelectedAttribute.bind(mockPresentationModelAdapter, context);
		
		adapterView.setSelection(POSITION_TO_SELECT);
	
		assertTrue(mockFunction.commandInvoked);
	}
	
	@Test
	public void whenSelectingAnItem_ThenInvokeCommandWithClickEvent()
	{
		OnItemSelectedAttribute onItemSelectedAttribute = new OnItemSelectedAttribute(adapterView, commandName);
		onItemSelectedAttribute.bind(mockPresentationModelAdapter, context);
		
		adapterView.setSelection(POSITION_TO_SELECT);

		assertThat(mockFunction.argsPassedToInvoke[0], instanceOf(ItemClickEvent.class));
		ItemClickEvent itemClickEvent = (ItemClickEvent)mockFunction.argsPassedToInvoke[0];
		assertTrue(itemClickEvent.getParent() == adapterView);
		assertThat(itemClickEvent.getPosition(), is(POSITION_TO_SELECT));
		assertThat(itemClickEvent.getView(), instanceOf(TextView.class));
	}
	
	@Test
	@Ignore
	//TODO Enable this test when the appropriate support has been added to Robolectric
	public void whenAllItemsAreRemovedFromAdapter_ThenInvokeCommandPassingClickEventWithPositionAsInvalidPosition()
	{
		OnItemSelectedAttribute onItemSelectedAttribute = new OnItemSelectedAttribute(adapterView, commandName);
		onItemSelectedAttribute.bind(mockPresentationModelAdapter, context);
		
		arrayAdapter.clear();
		arrayAdapter.notifyDataSetChanged();
		
		ItemClickEvent itemClickEvent = (ItemClickEvent)mockFunction.argsPassedToInvoke[0];
		assertThat(itemClickEvent.getPosition(), is(AdapterView.INVALID_POSITION));
	}

	private static class MockArrayAdapter extends ArrayAdapter<String>
	{
		public MockArrayAdapter(Context context, int textViewResourceId, List<String> data)
		{
			super(context, textViewResourceId, data);
		}
	}
}
