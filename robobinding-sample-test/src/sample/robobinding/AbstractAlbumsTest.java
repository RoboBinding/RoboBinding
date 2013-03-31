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

import sample.robobinding.model.Album;
import sample.robobinding.store.AlbumStore;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractAlbumsTest extends AbstractSampleAppTest
{
	protected void setUp() throws Exception
	{
		super.setUp();
		AlbumStore.emptyData();
	}
	
	protected void navigateToAlbumListScreen()
	{
		clickOnButtonWithLabel(R.string.albums);
	}
	
	protected Album setupAlbum(Album album)
	{
		AlbumStore.save(album);
		return album;
	}

	protected void assertAlbumContentMatches(Album album)
	{
		assertTrue(solo.searchText(album.getTitle()));
		assertTrue(solo.searchText(album.getArtist()));
		if(!album.isClassical())
		{
			assertTrue(solo.searchText("Not classical"));
		}
	}

	protected void assertNumberOfAlbums(int expectedAlbumSize)
	{
		int actualAlbumSize = solo.getCurrentListViews().get(0).getCount();
		assertEquals(expectedAlbumSize, actualAlbumSize);
	}
}
