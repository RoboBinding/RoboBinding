package org.robobinding.albumsampletest;

import org.robobinding.albumsample.R;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ViewAlbumsActivityTest extends AbstractAlbumsTest {
    public void testWhenNoAlbums_thenDisplayTheEmptyView() {
	navigateToAlbumListScreen();
	assertTrue(solo.searchText(getString(R.string.albums_list_empty)));
    }
}
