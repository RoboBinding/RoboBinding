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

import java.util.Map;
import java.util.Set;

import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.internal.com_google_common.collect.Sets;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.PropertyViewAttribute;

import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CheckedItemPositionsAttribute extends AbstractMultiTypePropertyViewAttribute<ListView>
{
	@Override
	protected PropertyViewAttribute<ListView> createPropertyViewAttribute(Class<?> propertyType)
	{
		if (SparseBooleanArray.class.isAssignableFrom(propertyType))
		{
			return new SparseBooleanArrayAttribute();
		}
		
		throw new RuntimeException("Could not find a suitable checkedItemPositions attribute class for property type: " + propertyType);
	}
	
	static class SparseBooleanArrayAttribute extends AbstractPropertyViewAttribute<ListView, SparseBooleanArray>
	{
		@Override
		protected void observeChangesOnTheView(final ValueModel<SparseBooleanArray> valueModel)
		{
			view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View itemView, int position, long id)
				{
					SparseBooleanArray array = view.getCheckedItemPositions();
					valueModel.setValue(array);
				}
			});
		}
		
		@Override
		protected void valueModelUpdated(SparseBooleanArray array)
		{
			for(int i=0; i<array.size(); i++)
			{
				view.setItemChecked(array.keyAt(i), array.valueAt(i));
			}
		}
	}
	
	static class IntegerSetAttribute extends AbstractPropertyViewAttribute<ListView, Set<Integer>>
	{
		@Override
		protected void observeChangesOnTheView(final ValueModel<Set<Integer>> valueModel)
		{
			view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View itemView, int clickedItemPosition, long id)
				{
					SparseBooleanArray array = view.getCheckedItemPositions();
					
					Set<Integer> checkedItemPositions = Sets.newHashSet();
					for(int i=0; i<array.size(); i++)
					{
						int position = array.keyAt(i);
						if(array.valueAt(position))
						{
							checkedItemPositions.add(position);
						}
					}
					
					valueModel.setValue(checkedItemPositions);
				}
			});
		}
		
		@Override
		protected void valueModelUpdated(Set<Integer> newValue)
		{
			for(int position : newValue)
			{
				view.setItemChecked(position, true);
			}
		}
	}
	
	static class MapAttribute extends AbstractPropertyViewAttribute<ListView, Map<Integer, Boolean>>
	{
		@Override
		protected void observeChangesOnTheView(final ValueModel<Map<Integer, Boolean>> valueModel)
		{
			view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View itemView, int clickedItemPosition, long id)
				{
					SparseBooleanArray array = view.getCheckedItemPositions();
					
					Map<Integer, Boolean> checkedItemPositions = Maps.newHashMap();
					for(int i=0; i<array.size(); i++)
					{
						checkedItemPositions.put(array.keyAt(i), array.valueAt(i));
					}
					
					valueModel.setValue(checkedItemPositions);
				}
			});
		}
		
		@Override
		protected void valueModelUpdated(Map<Integer, Boolean> newValue)
		{
			for(Integer position : newValue.keySet())
			{
				Boolean checked = newValue.get(position);
				view.setItemChecked(position, checked);
			}
		}
	}
}
