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
package robobinding.sample;

import sample.robobinding.HomeActivity;
import sample.robobinding.R;
import sample.robobinding.model.Album;
import sample.robobinding.store.AlbumStore;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractWorkflowTest extends ActivityInstrumentationTestCase2<HomeActivity>
{
	protected Solo solo;

	public AbstractWorkflowTest()
	{
		super("sample.robobinding", HomeActivity.class);
	}

	protected void createAnAlbumTests()
	{
		clickOnButtonWithLabel(homeButtonStringResId());
		
		clickOnButtonWithLabel(R.string.create);
		
		createNewAlbum();
		
		clickOnButtonWithLabel(R.string.save);
		
		assertNewAlbumIsVisible();
	}

	protected void editAnAlbumTests()
	{
		solo.clickOnText("Album name");
		
		assertTrue(solo.searchText("Classical"));
		assertTrue(solo.searchText("Composer name"));
		
		clickOnButtonWithLabel(R.string.edit);
		
		assertTrue(solo.isCheckBoxChecked(0));
		assertTrue(solo.searchEditText("Composer name"));
		
		solo.clearEditText(0);
		solo.enterText(0, "New album name");
		
		assertTrue(solo.searchText("Edit Classical Album"));
		solo.clickOnCheckBox(0);
		assertTrue(solo.searchText("Edit Album"));
		
		clickOnButtonWithLabel(R.string.save);
		
		assertTrue(solo.searchText("New album name"));
		assertTrue(solo.searchText("Not classical"));
		
		solo.goBack();
		
		assertTrue(solo.searchText("New album name"));
	}

	protected void deleteAlbumTests()
	{
		Album firstAlbum = AlbumStore.getAll().get(0);
		selectFirstAlbum();
		
		clickOnButtonWithLabel(R.string.delete);
		
		assertThatDeleteDialogTitleIsVisible();
		
		clickOnButtonWithLabel(R.string.yes);
		
		solo.waitForDialogToClose(500);
		
		assertAlbumHasBeenDeleted(firstAlbum);
	}
	
	protected void assertNewAlbumIsVisible()
	{
		assertTrue(solo.searchText("Album name"));
		assertTrue(solo.searchText("Artist name"));
	}

	private void createNewAlbum()
	{
		solo.enterText(0, "Album name");
		solo.enterText(1, "Artist name");
		solo.clickOnCheckBox(0);
		solo.enterText(2, "Composer name");
	}

	protected abstract int homeButtonStringResId();
	
	protected abstract void selectFirstAlbum();
	
	private void assertThatDeleteDialogTitleIsVisible()
	{
		assertTrue(solo.searchText(getActivity().getString(R.string.delete_album)));
	}
	
	private void assertAlbumHasBeenDeleted(Album firstAlbum)
	{
		assertFalse(solo.searchText(firstAlbum.getTitle()));
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		AlbumStore.resetData();
	}
	
	protected void clickOnButtonWithLabel(String label)
	{
		solo.clickOnButton(label);
	}

	protected void clickOnButtonWithLabel(int resId)
	{
		clickOnButtonWithLabel(getString(resId));
	}
	
	protected String getString(int resId)
	{
		return getActivity().getString(resId);
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
		solo.finishOpenedActivities();
	}
}
