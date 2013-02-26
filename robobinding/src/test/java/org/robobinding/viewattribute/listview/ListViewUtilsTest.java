/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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

import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.adapterview.MockArrayAdapter;

import android.app.Activity;
import android.widget.ListView;

import com.google.common.collect.Sets;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class ListViewUtilsTest
{
	private ListView listView;
	
	@Before
	public void setUp()
	{
		listView = new ListView(new Activity());
		listView.setAdapter(new MockArrayAdapter());
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
	
	@Test
	public void shouldSelectionsCleared()
	{
		setItemsChecked(anyCheckedItemPositions());
		
		ListViewUtils.clearSelections(listView);
		
		assertThat(SparseBooleanArrayUtils.toSet(listView.getCheckedItemPositions()), equalTo(Collections.<Integer>emptySet()));
	}

	private Set<Integer> anyCheckedItemPositions()
	{
		Set<Integer> result = Sets.newHashSet();
		for(int i=0; i<listView.getCount(); i++)
		{
			if(RandomValues.trueOrFalse())
			{
				result.add(i);
			}
		}
		return result;
	}
	
	private void setItemsChecked(Set<Integer> checkedItemPositions)
	{
		for(Integer checkedItemPosition : checkedItemPositions)
		{
			listView.setItemChecked(checkedItemPosition, true);
		}
	}
}
