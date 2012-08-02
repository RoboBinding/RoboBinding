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
package org.robobinding.viewattribute.listview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.robobinding.viewattribute.RandomValues.nextInt;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.R;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.adapterview.MockAdapterViewListeners;
import org.robobinding.viewattribute.adapterview.MockArrayAdapter;
import org.robobinding.viewattribute.view.AbstractPropertyViewAttributeWithViewListenersAwareTest;

import android.widget.ListAdapter;
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class CheckedItemPositionAttributeTest extends AbstractPropertyViewAttributeWithViewListenersAwareTest<ListView, CheckedItemPositionAttribute, MockAdapterViewListeners>
{
	private int checkedItemPosition;
	
	@Before
	public void setUp()
	{
		super.initializeViewAndAttribute();
		super.initializeViewListeners();
		
		ListAdapter adapter = new MockArrayAdapter(R.layout.simple_list_item_single_choice);
		view.setAdapter(adapter);
		view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		checkedItemPosition = nextInt(adapter.getCount());
	}
	
	@Test
	public void whenValueModelUpdated_thenViewShouldReflectChanges()
	{
		attribute.valueModelUpdated(checkedItemPosition);
		
		assertThat(view.getCheckedItemPosition(), equalTo(checkedItemPosition));
	}
	
	@Test
	public void whenCheckedItemPositionChanged_thenValueModelUpdatedAccordingly()
	{
		ValueModel<Integer> valueModel = twoWayBindToProperty(Integer.class);
		
		setItemChecked();
		
		assertThat(valueModel.getValue(), equalTo(checkedItemPosition));
	}

	private void setItemChecked()
	{
		view.performItemClick(view, checkedItemPosition, 0);
	}
	
	@Test
	public void whenTwoWayBinding_thenRegisterWithMulticastListener()
	{
		twoWayBindToProperty(Integer.class);
		
		assertTrue(viewListeners.addOnItemClickListenerInvoked);
	}
}