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
package sample.robobinding;

import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class DynamicItemLayoutListViewTest extends AbstractSampleAppTest
{
	public void testChangingItemLayout()
	{
		clickOnButtonWithLabel(R.string.dynamic_itemlayout_albums_listview);
		solo.waitForView(ListView.class);
		int heightOfListViewItem = getHeightOfFirstItemInListView();
		
		clickOnButtonWithLabel(R.string.toggle_itemlayout);
		
		int heightOfNewListViewItem = getHeightOfFirstItemInListView();
		assertFalse(heightOfListViewItem == heightOfNewListViewItem);
	}

	private int getHeightOfFirstItemInListView()
	{
		return solo.getCurrentListViews().get(0).getChildAt(0).getHeight();
	}
	
}
