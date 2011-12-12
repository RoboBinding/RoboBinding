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
package sample.robobinding;

import sample.robobinding.R;



/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class SpinnerAlbumsTest extends AbstractWorkflowTest
{
	@Override
	protected int homeButtonStringResId()
	{
		return R.string.spinner_albums;
	}

	public void testCreatingAndDeletingAnAlbum()
	{
		createAnAlbumTests();
		
		deleteAlbumTests();
	}
	
	@Override
	protected void assertNewAlbumIsVisible()
	{
		clickOnAlbumSpinner();
		
		assertTrue(solo.searchText("Album name"));
		assertTrue(solo.searchText("Artist name"));
		
		solo.goBack();
	}

	@Override
	protected void selectFirstAlbum()
	{
		clickOnAlbumSpinner();

		clickFirstItemInDropdownList();
		
		clickOnButtonWithLabel(R.string.view);
	}

	protected void clickOnAlbumSpinner()
	{
		solo.clickOnText("HQ");
	}
	
	private void clickFirstItemInDropdownList()
	{
		solo.clickInList(0);
	}
}
