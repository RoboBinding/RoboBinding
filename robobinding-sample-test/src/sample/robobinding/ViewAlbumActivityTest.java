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

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ViewAlbumActivityTest extends AbstractAlbumsTest {
    public void testWhenViewAnNonClassicalAlbum_thenTheAlbumIsCorrectlyDisplayed() {
	Album album = setupNonClassicalAlbum();
	navigateToAlbumListScreen();

	viewAlbum(album);

	assertAlbumContentMatches(album);
    }

    private Album setupNonClassicalAlbum() {
	Album album = AlbumTestData.createNonClassicalAlbum();
	return setupAlbum(album);
    }

    private void viewAlbum(Album album) {
	solo.clickOnText(album.getTitle());
    }

    public void testWhenViewAnClassicalAlbum_thenTheAlbumIsCorrectlyDisplayed() {
	Album album = setupClassicalAlbum();
	navigateToAlbumListScreen();

	viewAlbum(album);

	assertAlbumContentMatches(album);
    }

    private Album setupClassicalAlbum() {
	Album album = AlbumTestData.createClassicalAlbum();
	return setupAlbum(album);
    }

}
