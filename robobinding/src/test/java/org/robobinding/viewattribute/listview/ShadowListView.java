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

import android.util.SparseBooleanArray;
import android.widget.ListView;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Implements(ListView.class)
public class ShadowListView extends com.xtremelabs.robolectric.shadows.ShadowListView
{
	private int choiceMode;
	private int checkedItemPosition = ListView.INVALID_POSITION;
	private SparseBooleanArray checkedItemPositions = new SparseBooleanArray();
	
    @Implementation
	public int getChoiceMode()
	{
		return choiceMode;
	}
	
    @Implementation
	public void setChoiceMode(int choiceMode)
	{
		this.choiceMode = choiceMode;
	}
	
    @Implementation
	public void setItemChecked(int position, boolean value)
	{
		if(choiceMode == ListView.CHOICE_MODE_SINGLE)
		{
			checkedItemPosition = position;
		} else if(choiceMode == ListView.CHOICE_MODE_MULTIPLE)
		{
			checkedItemPositions.put(position, value);
		}
		performItemClick(position);
	}
	
    @Implementation
	public int getCheckedItemPosition()
	{
		return checkedItemPosition;
	}
	
    @Implementation
	public SparseBooleanArray getCheckedItemPositions()
	{
		return checkedItemPositions;
	}
}
