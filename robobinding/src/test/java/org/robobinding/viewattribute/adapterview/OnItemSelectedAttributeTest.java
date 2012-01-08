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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.robobinding.viewattribute.AbstractCommandViewAttributeTest;
import org.robobinding.viewattribute.RandomValues;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnItemSelectedAttributeTest extends AbstractCommandViewAttributeTest<ListView, OnItemSelectedAttribute>
{
	private int indexToSelect;
	private ArrayAdapter<String> arrayAdapter;
	
	@Before
	public void setUp()
	{
		arrayAdapter = new MockArrayAdapter();
		view.setAdapter(arrayAdapter);
		indexToSelect = RandomValues.anyIndex(arrayAdapter);
	}
	
	@Test
	public void givenBoundAttribute_whenSelectingAnItem_thenEventReceived()
	{
		bindAttribute();

		selectAnItem();

		assertEventReceived();
	}

	private void selectAnItem()
	{
		view.setSelection(indexToSelect);
	}

	private void assertEventReceived()
	{
		assertEventReceived(ItemClickEvent.class);
		ItemClickEvent itemClickEvent = getEventReceived();
		assertTrue(itemClickEvent.getParent() == view);
		assertThat(itemClickEvent.getPosition(), is(indexToSelect));
		assertThat(itemClickEvent.getView(), instanceOf(TextView.class));
	}
	
	@Test
	@Ignore
	//TODO Enable this test when the appropriate support has been added to Robolectric
	public void whenAllItemsAreRemovedFromAdapter_thenInvokeCommandPassingClickEventWithPositionAsInvalidPosition()
	{
		bindAttribute();
		
		arrayAdapter.clear();
		arrayAdapter.notifyDataSetChanged();
		
		assertEventReceived(ItemClickEvent.class);
		ItemClickEvent itemClickEvent = getEventReceived();
		assertThat(itemClickEvent.getPosition(), is(AdapterView.INVALID_POSITION));
	}

}
