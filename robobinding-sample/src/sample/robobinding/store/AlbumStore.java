/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package sample.robobinding.store;

import java.util.Collections;
import java.util.List;

import org.robobinding.internal.com_google_common.collect.Lists;

import sample.robobinding.model.Album;
import sample.robobinding.presentationmodel.AlbumCursor;


/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class AlbumStore
{
	private static List<Album> albums;

	static
	{
		resetData();
	}
	
	private static Album createClassical(String title, String artist, String composer)
	{
		Album.Builder builder = initializeBuilder(title, artist);
		builder.setClassical(true).setComposer(composer);
		return builder.create();
	}

	private static Album createNonClassical(String title, String artist)
	{
		Album.Builder builder = initializeBuilder(title, artist);
		return builder.create();
	}

	private static Album.Builder initializeBuilder(String title, String artist)
	{
		Album.Builder builder = new Album.Builder();
		builder.setTitle(title).setArtist(artist);
		return builder;
	}

	public static Album get(long albumId)
	{
		for (Album album : albums)
		{
			if (album.getId() == albumId)
				return album;
		}
		
		throw new IllegalArgumentException("No such album for id: " + albumId);
	}

	public static void save(Album album)
	{
		if (album.isNew())
		{	
			album.setId(nextId());
			albums.add(album);
		}
		
		int index = albums.indexOf(album);
		albums.remove(index);
		albums.add(index, album);
	}

	private static long nextId()
	{
		return albums.size() + 1;
	}
	
	public static List<Album> getAll()
	{
		return Collections.unmodifiableList(albums);
	}

	public static AlbumCursor getCursor()
	{
		return new AlbumCursor(getAll());
	}

	public static void resetData()
	{
		albums = Lists.newArrayList();
		
		save(createNonClassical("HQ", "Roy Harper"));
		save(createNonClassical("The Rough Dancer and Cyclical Night", "Astor Piazzola"));
		save(createNonClassical("The Black Light", "Calexico"));
		save(createNonClassical("Stormcock", "Roy Harper"));
		save(createClassical("Symphony No.5", "CBSO", "Sibelius"));
		save(createNonClassical("Greatest Hits", "Queen"));
		save(createClassical("Symphony No.5", "Beethoven", "Beethoven"));
		save(createNonClassical("Dire Straits", "Dire Straits"));
		save(createNonClassical("Like a Virgin", "Madonna"));
	}

	public static Album getByIndex(int position)
	{
		return albums.get(position);
	}

	public static void delete(Album album)
	{
		albums.remove(album);
	}

}
