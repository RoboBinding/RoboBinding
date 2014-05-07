package org.robobinding.albumsampletest;

import java.util.Random;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.model.Album;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class CreateEditAlbumActivityTest extends AbstractAlbumsTest {
    public void testWhenCreateAnAlbum_thenTheAlbumIsCreated() {
	navigateToAlbumListScreen();
	Album album = AlbumTestData.createAlbum();
	createAlbum(album);

	assertAlbumExists(album);
    }

    private void createAlbum(Album album) {
	clickOnButtonWithLabel(R.string.create);

	inputAlbumDetails(album);

	clickOnButtonWithLabel(R.string.save);
    }

    private void assertAlbumExists(Album album) {
	assertTrue(solo.searchText(album.getTitle()));
	assertTrue(solo.searchText(album.getArtist()));
    }

    public void testWhenCreateAlbums_thenCorrectNumberOfAlbumsAreCreated() {
	navigateToAlbumListScreen();
	int numAlbums = randomNumberOfAlbums();
	createAlbums(numAlbums);

	assertNumberOfAlbums(numAlbums);
    }

    private int randomNumberOfAlbums() {
	return new Random().nextInt(5) + 1;
    }

    private void createAlbums(int numAlbums) {
	for (int i = 0; i < numAlbums; i++) {
	    createAlbum(AlbumTestData.createAlbum());
	}
    }

    private void inputAlbumDetails(Album album) {
	solo.enterText(0, album.getTitle());
	solo.enterText(1, album.getArtist());
	if (solo.isCheckBoxChecked(0) != album.isClassical()) {
	    solo.clickOnCheckBox(0);
	}
	if (album.isClassical()) {
	    solo.enterText(2, album.getComposer());
	}
    }

    public void testWhenEditAnAlbum_thenWindowTitleReflectAlbumClassicalState() {
	Album album = AlbumTestData.createClassicalAlbum();
	setupAlbum(album);
	navigateToAlbumListScreen();

	editAlbum(album);
	assertTitleReads("Edit Classical Album");
	clickOnClassicalCheckBox();
	assertTitleReads("Edit Album");
    }

    private void editAlbum(Album album) {
	solo.clickOnText(album.getTitle());
	clickOnButtonWithLabel(R.string.edit);
    }

    private void assertTitleReads(String title) {
	while (solo.scrollUp()) {
	}
	assertTrue(solo.searchText(title));
    }

    private void clickOnClassicalCheckBox() {
	solo.clickOnCheckBox(0);
    }

    public void testWhenEditAnAlbum_thenTheAlbumIsUpdated() {
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
