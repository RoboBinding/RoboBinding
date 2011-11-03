/**
 * AlbumDao.java
 * 10 Oct 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.sample.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import robobinding.internal.com_google_common.collect.Lists;
import robobinding.internal.com_google_common.collect.Sets;
import robobinding.sample.model.Album;
import robobinding.sample.presentationmodel.AlbumCursor;


/**
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 *
 */
public class AlbumDao
{
	private Set<Album> albums;

	public AlbumDao()
	{
		albums = Sets.newLinkedHashSet();
		
		save(createNonClassical("Roy Harper", "HQ"));
		save(createNonClassical("The Rough Dancer and Cyclical Night", "Astor Piazzola"));
		save(createNonClassical("The Black Light", "Calexico"));
		save(createClassical("Symphony No.5", "CBSO", "Sibelius"));
	}
	
	private Album createClassical(String title, String artist, String composer)
	{
		Album.Builder builder = initializeBuilder(title, artist);
		builder.setClassical(true).setComposer(composer);
		return builder.create();
	}

	private Album createNonClassical(String title, String artist)
	{
		Album.Builder builder = initializeBuilder(title, artist);
		return builder.create();
	}

	private Album.Builder initializeBuilder(String title, String artist)
	{
		Album.Builder builder = new Album.Builder();
		builder.setTitle(title).setArtist(artist);
		return builder;
	}

	public Album get(long albumId)
	{
		for (Album album : albums)
		{
			if (album.getId() == albumId)
				return album;
		}
		
		throw new IllegalArgumentException("No such album for id: " + albumId);
	}

	public void save(Album album)
	{
		if (album.isNew())
			album.setId(nextId());
		
		albums.add(album);
	}

	private long nextId()
	{
		return albums.size() + 1;
	}
	
	public Collection<Album> getAll()
	{
		return Collections.unmodifiableCollection(albums);
	}

	public AlbumCursor getCursor()
	{
		List<Album> albumsList = Lists.newArrayList(albums);
		return new AlbumCursor(albumsList);
	}

}
