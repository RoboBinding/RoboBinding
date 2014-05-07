package org.robobinding.albumsampletest;

import java.util.Random;

import org.robobinding.albumsample.model.Album;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class AlbumTestData {
    public static Album createAlbum() {
	boolean classical = new Random().nextBoolean();

	if (classical) {
	    return createClassicalAlbum();
	} else {
	    return createNonClassicalAlbum();
	}
    }

    public static Album createClassicalAlbum() {
	int index = nextUniqueIndex();
	Album.Builder albumBuilder = newAlbumBuilder(index);
	albumBuilder.setClassical(true);
	albumBuilder.setComposer("Composer " + index);
	return albumBuilder.create();
    }

    public static Album createNonClassicalAlbum() {
	int index = nextUniqueIndex();
	Album.Builder albumBuilder = newAlbumBuilder(index);
	return albumBuilder.create();
    }

    private static Album.Builder newAlbumBuilder(int index) {
	Album.Builder albumBuilder = new Album.Builder();
	albumBuilder.setArtist("Artist " + index);
	albumBuilder.setTitle("Album " + index);
	return albumBuilder;
    }

    private static int uniqueIndex = 1;

    private static int nextUniqueIndex() {
	return uniqueIndex++;
    }
}
