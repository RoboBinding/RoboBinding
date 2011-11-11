/**
 * CreateAlbumPresentationModel.java
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
package robobinding.sample.presentationmodel;

import robobinding.CustomSetter;
import robobinding.DependsOn;
import robobinding.presentationmodel.AbstractPresentationModel;
import robobinding.sample.R;
import robobinding.sample.dao.AlbumDao;
import robobinding.sample.model.Album;
import android.app.Activity;

/**
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 *
 */
@PresentationModel
public class CreateEditAlbumPresentationModel extends AbstractPresentationModel
{
	private static final String TITLE = "title";
	private static final String ARTIST = "artist";
	private static final String COMPOSER = "composer";
	private static final String CLASSICAL = "classical";
	
	private Album.Builder albumBuilder;
	private AlbumDao albumDao;
	private Activity activity;
	
	public CreateEditAlbumPresentationModel(Activity activity, AlbumDao albumDao, long albumId)
	{
		initialize(activity, albumDao);
		Album album = albumDao.get(albumId);
		albumBuilder = album.createBuilder();
	}
	
	public CreateEditAlbumPresentationModel(Activity activity, AlbumDao albumDao)
	{
		initialize(activity, albumDao);
		albumBuilder = new Album.Builder();
	}
	
	private void initialize(Activity activity, AlbumDao albumDao)
	{
		this.activity = activity;
		this.albumDao = albumDao;
	}
	
	@DependsOn(CLASSICAL)
	public boolean isComposerEnabled()
	{
		return isClassical();
	}
	
	public void save()
	{
		albumDao.save(albumBuilder.create());
		activity.finish();
	}
	
	public String getTitle()
	{
		return albumBuilder.getTitle();
	}
	
	@CustomSetter
	public void setTitle(String title)
	{
		String oldValue = albumBuilder.getTitle();
		albumBuilder.setTitle(title);
		
		firePropertyChange(TITLE, oldValue, title);
	}

	public String getArtist()
	{
		return albumBuilder.getArtist();
	}

	public void setArtist(String artist)
	{
		String oldValue = albumBuilder.getArtist();
		albumBuilder.setArtist(artist);
		
		firePropertyChange(ARTIST, oldValue, artist);
	}

	public boolean isClassical()
	{
		return albumBuilder.isClassical();
	}

	public void setClassical(boolean classical)
	{
		boolean oldValue = albumBuilder.isClassical();
		albumBuilder.setClassical(classical);
		
		firePropertyChange(CLASSICAL, oldValue, classical);
	}

	public String getComposer()
	{
		return albumBuilder.getComposer();
	}

	public void setComposer(String composer)
	{
		String oldValue = albumBuilder.getComposer();
		albumBuilder.setComposer(composer);
		
		firePropertyChange(COMPOSER, oldValue, composer);
	}

	@DependsOn(CLASSICAL)
	public String getWindowTitle()
	{
		if(albumBuilder.isNew())
			return activity.getString(R.string.create_album);
		
		return isClassical() ? "Edit Classical Album" : "Edit Album";
	}
}
