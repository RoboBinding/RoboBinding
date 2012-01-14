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

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.internal.com_google_common.collect.Sets;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.RandomValues;

import android.R;
import android.util.SparseBooleanArray;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.xtremelabs.robolectric.Robolectric;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractCheckedItemPositionsAttributeTest<ViewType extends ListView, PropertyViewAttributeType extends PropertyViewAttribute<? super ViewType>> extends AbstractPropertyViewAttributeTest<ViewType, PropertyViewAttributeType>
{
	private ListAdapter adapter;
	@Before
	public void setUp()
	{
		Robolectric.bindShadowClass(ShadowListView.class);
		Robolectric.bindShadowClass(ShadowSparseBooleanArray.class);
		super.initializeViewAndAttribute();
		
		adapter = new MockArrayAdapter(R.layout.simple_list_item_multiple_choice);
		view.setAdapter(adapter);
		view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
	
	protected void setItemsChecked(Set<Integer> checkedItemPositions)
	{
		for(Integer checkedItemPosition : checkedItemPositions)
		{
			view.setItemChecked(checkedItemPosition, true);
		}
	}
	
	protected SparseBooleanArray anySparseBooleanArray()
	{
		SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
		for(int i=0; i<adapter.getCount(); i++)
		{
			int selector = RandomValues.nextInt(3);
			if(selector == 0)
			{
				sparseBooleanArray.put(i, true);
			}else if(selector == 1)
			{
				sparseBooleanArray.put(i, false);
			}else
			{
			}
		}
		return sparseBooleanArray;
	}
	
	protected void assertSparseBooleanArrayEquals(SparseBooleanArray expected, SparseBooleanArray actual)
	{
		Set<Integer> expectedSet = asSet(expected);
		Set<Integer> actualSet = asSet(actual);
		assertEquals(expectedSet, actualSet);
	}

	
	protected void assertMapEquals(Map<Integer, Boolean> expected, Map<Integer, Boolean> actual)
	{
		Set<Integer> expectedSet = asSet(expected);
		Set<Integer> actualSet = asSet(actual);
		assertEquals(expectedSet, actualSet);
	}
	
	protected Set<Integer> asSet(SparseBooleanArray array)
	{
		Set<Integer> trueSet = Sets.newHashSet();
		for(int i=0; i<array.size(); i++)
		{
			if(array.valueAt(i))
			{
				trueSet.add(array.keyAt(i));
			}
		}
		return trueSet;
	}
	
	protected Set<Integer> asSet(Map<Integer, Boolean> map)
	{
		Set<Integer> trueSet = Sets.newHashSet();
		for(Integer key : map.keySet())
		{
			if(map.get(key))
			{
				trueSet.add(key);
			}
		}
		return trueSet;
	}
	
	protected Map<Integer, Boolean> asMap(SparseBooleanArray array)
	{
		Map<Integer, Boolean> map = Maps.newHashMap();
		for(int i=0; i<array.size(); i++)
		{
			map.put(array.keyAt(i), array.valueAt(i));
		}
		return map;
	}
}
