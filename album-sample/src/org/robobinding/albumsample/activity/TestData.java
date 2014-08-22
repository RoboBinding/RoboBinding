package org.robobinding.albumsample.activity;

import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.store.AlbumStore;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TestData {
    public void setUp(AlbumStore albumStore) {
	albumStore.clear();
	
	albumStore.save(createNonClassical("HQ", "Roy Harper"));
	albumStore.save(createNonClassical("The Rough Dancer and Cyclical Night", "Astor Piazzola"));
	albumStore.save(createNonClassical("The Black Light", "Calexico"));
	albumStore.save(createNonClassical("Stormcock", "Roy Harper"));
	albumStore.save(createClassical("Symphony No.5", "CBSO", "Sibelius"));
	albumStore.save(createNonClassical("Greatest Hits", "Queen"));
	albumStore.save(createClassical("Symphony No.5", "Beethoven", "Beethoven"));
	albumStore.save(createNonClassical("Dire Straits", "Dire Straits"));
	albumStore.save(createNonClassical("Like a Virgin", "Madonna"));
    }
    
    private static Album createClassical(String title, String artist, String composer) {
	Album.Builder builder = initializeBuilder(title, artist);
	builder.setClassical(true).setComposer(composer);
	return builder.create();
    }

    private static Album createNonClassical(String title, String artist) {
	Album.Builder builder = initializeBuilder(title, artist);
	return builder.create();
    }

    private static Album.Builder initializeBuilder(String title, String artist) {
	Album.Builder builder = new Album.Builder();
	builder.setTitle(title).setArtist(artist);
	return builder;
    }

}
