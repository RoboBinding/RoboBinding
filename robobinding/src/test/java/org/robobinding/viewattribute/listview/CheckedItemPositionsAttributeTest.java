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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.viewattribute.adapterview.AdapterViewListeners;
import org.robobinding.viewattribute.listview.CheckedItemPositionsAttribute.AbstractCheckedItemPositionsAttribute;
import org.robobinding.viewattribute.listview.CheckedItemPositionsAttribute.MapCheckedItemPositionsAttribute;
import org.robobinding.viewattribute.listview.CheckedItemPositionsAttribute.SetCheckedItemPositionsAttribute;
import org.robobinding.viewattribute.listview.CheckedItemPositionsAttribute.SparseBooleanArrayCheckedItemPositionsAttribute;

import android.util.SparseBooleanArray;
import android.widget.AdapterView.OnItemClickListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CheckedItemPositionsAttributeTest extends AbstractMultiTypePropertyViewAttributeTest<CheckedItemPositionsAttribute>
{
	@Override
	protected void setTypeMappingExpectations()
	{
		forPropertyType(SparseBooleanArray.class).expectAttribute(SparseBooleanArrayCheckedItemPositionsAttribute.class);
		forPropertyType(Set.class).expectAttribute(SetCheckedItemPositionsAttribute.class);
		forPropertyType(Map.class).expectAttribute(MapCheckedItemPositionsAttribute.class);
	}
	
	@Test
	public void whenBinding_thenRegisterWithMulticastListener()
	{
		AbstractCheckedItemPositionsAttributeForTest abstractCheckedItemPositionsAttributeForTest = new AbstractCheckedItemPositionsAttributeForTest();
		AdapterViewListeners mockAdapterViewListeners = mock(AdapterViewListeners.class);
		abstractCheckedItemPositionsAttributeForTest.setViewListeners(mockAdapterViewListeners);
		
		abstractCheckedItemPositionsAttributeForTest.observeChangesOnTheView(null);
		
		verify(mockAdapterViewListeners).addOnItemClickListener(any(OnItemClickListener.class));
	}
	
	public class AbstractCheckedItemPositionsAttributeForTest extends AbstractCheckedItemPositionsAttribute<Integer>
	{
		@Override
		protected void viewCheckedItemPositionsChanged(ValueModel<Integer> valueModel)
		{
		}

		@Override
		protected void valueModelUpdated(Integer newValue)
		{
		}
	}
}