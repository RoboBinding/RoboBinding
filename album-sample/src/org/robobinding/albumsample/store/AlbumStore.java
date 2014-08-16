package org.robobinding.albumsample.store;

import java.util.Collections;
import java.util.List;

import org.robobinding.albumsample.model.Album;
import org.robobinding.internal.guava.Lists;

/**
 *
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class AlbumStore {
	private static List<Album> albums;

	static {
		resetData();
	}

	private static Album createClassical(String title, String artist,
			String composer) {
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

	public static Album get(long albumId) {
		for (Album album : albums) {
			if (album.getId() == albumId)
				return album;
		}

		throw new IllegalArgumentException("No such album for id: " + albumId);
	}

	public static void save(Album album) {
		if (album.isNew()) {
			album.setId(nextId());
			albums.add(album);
		}

		int index = albums.indexOf(album);
		albums.remove(index);
		albums.add(index, album);
	}

	private static long nextId() {
		return albums.size() + 1;
	}

	public static List<Album> getAll() {
		return Collections.unmodifiableList(albums);
	}

	public static void resetData() {
		albums = Lists.newArrayList();

		save(createNonClassical("HQ", "Roy Harper"));
		save(createNonClassical("The Rough Dancer and Cyclical Night",
				"Astor Piazzola"));
		save(createNonClassical("The Black Light", "Calexico"));
		save(createNonClassical("Stormcock", "Roy Harper"));
		save(createClassical("Symphony No.5", "CBSO", "Sibelius"));
		save(createNonClassical("Greatest Hits", "Queen"));
		save(createClassical("Symphony No.5", "Beethoven", "Beethoven"));
		save(createNonClassical("Dire Straits", "Dire Straits"));
		save(createNonClassical("Like a Virgin", "Madonna"));
	}

	public static void emptyData() {
		albums = Lists.newArrayList();
	}

	public static Album getByIndex(int position) {
		return albums.get(position);
	}

	public static void delete(Album album) {
		albums.remove(album);
	}

}
