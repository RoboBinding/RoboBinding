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
public class DynamicDropdownLayoutSpinnerTest extends AbstractSampleAppTest
{
	public void testChangingItemLayout()
	{
		clickOnButtonWithLabel(R.string.dynamic_dropdownlayout_albums_spinner);
		clickOnAlbumSpinner();
		int heightOfListViewItem = getHeightOfFirstItemInListView();
		solo.goBack();
		
		clickOnButtonWithLabel(R.string.toggle_itemlayout);
		clickOnAlbumSpinner();
		
		int heightOfNewListViewItem = getHeightOfFirstItemInListView();
		assertFalse(heightOfListViewItem == heightOfNewListViewItem);
	}

	private int getHeightOfFirstItemInListView()
	{
		solo.waitForView(ListView.class);
		return solo.getCurrentListViews().get(0).getChildAt(0).getHeight();
	}
	
	protected void clickOnAlbumSpinner()
	{
		solo.clickOnText("HQ");
	}
}
