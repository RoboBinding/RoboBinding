package org.robobinding.albumsampletest;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.store.AlbumStore;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractAlbumsTest extends AbstractSampleAppTest {
    protected void setUp() throws Exception {
	super.setUp();
	AlbumStore.emptyData();
    }

    protected void navigateToAlbumListScreen() {
	clickOnButtonWithLabel(R.string.albums);
    }

    protected Album setupAlbum(Album album) {
	AlbumStore.save(album);
	return album;
    }

    protected void assertAlbumContentMatches(Album album) {
	assertTrue(solo.searchText(album.getTitle()));
	assertTrue(solo.searchText(album.getArtist()));
	if (!album.isClassical()) {
	    assertTrue(solo.searchText("Not classical"));
	}
    }

    protected void assertNumberOfAlbums(int expectedAlbumSize) {
	solo.waitForActivity("ViewAlbumsActivity", 2000);
	int actualAlbumSize = solo.getCurrentListViews().get(0).getCount();
	assertEquals(expectedAlbumSize, actualAlbumSize);
    }
}
