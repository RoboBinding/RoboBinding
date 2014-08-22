package org.robobinding.albumsampletest;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.activity.ViewAlbumsActivity;
import org.robobinding.albumsample.model.Album;

import android.widget.ListView;


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
	albumStore.clear();
    }

    protected void navigateToAlbumListScreen() {
	clickOnButtonWithLabel(R.string.albums);
    }

    protected Album setupAlbum(Album album) {
	albumStore.save(album);
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
	solo.waitForActivity(ViewAlbumsActivity.class, 2000);
	ListView albumListView = (ListView)solo.getView(R.id.albumListView);
	int actualAlbumSize = albumListView.getCount();
	assertEquals(expectedAlbumSize, actualAlbumSize);
    }
}
