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
