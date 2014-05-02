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
package org.robobinding.albumsampletest;

import java.util.Random;

import org.robobinding.albumsample.R;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class DeleteAlbumActivityTest extends AbstractAlbumsTest {
    public void testWhenDeleteAllAlbums_thenTheAlbumsAreDeleted() {
	int numAlbums = randomNumberOfAlbums();
	setupAlbums(numAlbums);
	navigateToAlbumListScreen();

	deleteFirstAlbums(numAlbums);

	assertNumberOfAlbums(0);
    }

    private int randomNumberOfAlbums() {
	return new Random().nextInt(5) + 1;
    }

    private void setupAlbums(int numAlbums) {
	for (int i = 0; i < numAlbums; i++) {
	    setupAlbum(AlbumTestData.createAlbum());
	}
    }

    private void deleteFirstAlbums(int numAlbums) {
	for (int i = 0; i < numAlbums; i++) {
	    deleteFirstAlbum();
	}
    }

    private void deleteFirstAlbum() {
	selectFirstAlbum();

	clickOnButtonWithLabel(R.string.delete);

	assertThatDeleteDialogTitleIsVisible();

	clickOnButtonWithLabel(R.string.yes);

	solo.waitForDialogToClose(500);
    }

    private void selectFirstAlbum() {
	scrollToTopOfList();
	solo.clickInList(0);
    }

    private void scrollToTopOfList() {
	while (solo.scrollUpList(0)) {
	}
    }

    private void assertThatDeleteDialogTitleIsVisible() {
	assertTrue(solo.searchText(getActivity().getString(R.string.delete_album)));
    }
}
