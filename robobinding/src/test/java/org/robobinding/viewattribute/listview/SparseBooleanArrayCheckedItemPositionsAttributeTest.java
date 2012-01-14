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

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.listview.CheckedItemPositionsAttribute.SparseBooleanArrayCheckedItemPositionsAttribute;

import android.util.SparseBooleanArray;
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SparseBooleanArrayCheckedItemPositionsAttributeTest extends AbstractCheckedItemPositionsAttributeTest<ListView, SparseBooleanArrayCheckedItemPositionsAttribute>
{
	private SparseBooleanArray checkedItemPositions;
	
	@Before
	public void setUp()
	{
		super.setUp();
		
		checkedItemPositions = anySparseBooleanArray();
	}
	@Test
	public void whenValueModelUpdated_thenViewShouldReflectChanges()
	{
		attribute.valueModelUpdated(checkedItemPositions);
		
		assertSparseBooleanArrayEquals(checkedItemPositions, view.getCheckedItemPositions());
	}
	
	@Test
	public void whenCheckedItemPositionChanged_thenValueModelUpdatedAccordingly()
	{
		ValueModel<SparseBooleanArray> valueModel = twoWayBindToProperty(SparseBooleanArray.class);
		
		setItemsChecked(asSet(checkedItemPositions));
		
		assertSparseBooleanArrayEquals(checkedItemPositions, valueModel.getValue());
	}

}
