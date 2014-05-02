package org.robobinding.albumsampletest;

import org.robobinding.albumsample.model.Album;

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
