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

import java.util.Random;

import sample.robobinding.model.Album;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class CreateEditAlbumActivityTest extends AbstractAlbumsTest
{
	public void testWhenCreateAnAlbum_thenTheAlbumIsCreated()
	{
		navigateToAlbumListScreen();
		Album album = AlbumTestData.createAlbum();
		createAlbum(album);
		
		assertAlbumExists(album);
	}
	
	private void createAlbum(Album album)
	{
		clickOnButtonWithLabel(R.string.create);
		
		inputAlbumDetails(album);
		
		clickOnButtonWithLabel(R.string.save);
	}

	private void assertAlbumExists(Album album)
	{
		assertTrue(solo.searchText(album.getTitle()));
		assertTrue(solo.searchText(album.getArtist()));
	}
	
	public void testWhenCreateAlbums_thenCorrectNumberOfAlbumsAreCreated()
	{
		navigateToAlbumListScreen();
		int numAlbums = randomNumberOfAlbums();
		createAlbums(numAlbums);
		
		assertNumberOfAlbums(numAlbums);
	}
	
	private int randomNumberOfAlbums()
	{
		return new Random().nextInt(5)+1;
	}

	private void createAlbums(int numAlbums)
	{
		for(int i=0; i<numAlbums; i++)
		{
			createAlbum(AlbumTestData.createAlbum());
		}
	}
	
	private void inputAlbumDetails(Album album)
	{
		solo.enterText(0, album.getTitle());
		solo.enterText(1, album.getArtist());
		if(solo.isCheckBoxChecked(0) != album.isClassical())
		{
			solo.clickOnCheckBox(0);
		}
		if(album.isClassical())
		{
			solo.enterText(2, album.getComposer());
		}
	}
	
	public void testWhenEditAnAlbum_thenWindowTitleReflectAlbumClassicalState()
	{
		Album album = AlbumTestData.createClassicalAlbum();
		setupAlbum(album);
		navigateToAlbumListScreen();
		
		editAlbum(album);
		assertTitleReads("Edit Classical Album");
		clickOnClassicalCheckBox();
		assertTitleReads("Edit Album");
	}

	private void editAlbum(Album album)
	{
		solo.clickOnText(album.getTitle());
		clickOnButtonWithLabel(R.string.edit);
	}

	private void assertTitleReads(String title)
	{
		while(solo.scrollUp()){}
		assertTrue(solo.searchText(title));
	}
	
	private void clickOnClassicalCheckBox()
	{
		solo.clickOnCheckBox(0);
	}

	public void testWhenEditAnAlbum_thenTheAlbumIsUpdated()
	{
		Album album = AlbumTestData.createClassicalAlbum();
		setupAlbum(album);
		navigateToAlbumListScreen();
		
		editAlbum(album);
		Album newAlbum = AlbumTestData.createNonClassicalAlbum();
		inputAlbumDetails(newAlbum);
		clickOnButtonWithLabel(R.string.save);
		
		assertAlbumContentMatches(album);
	}
}
