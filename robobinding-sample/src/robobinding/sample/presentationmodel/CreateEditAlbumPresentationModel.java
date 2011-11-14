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
import robobinding.NotifyPropertyChange;
import robobinding.presentationmodel.AbstractPresentationModel;
import robobinding.presentationmodel.PresentationModelRefresh;
import robobinding.sample.R;
import robobinding.sample.model.Album;
import robobinding.sample.store.AlbumStore;
import android.app.Activity;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@NotifyPropertyChange
public class CreateEditAlbumPresentationModel extends AbstractPresentationModel
{
	private static final String TITLE = "title";
	private static final String ARTIST = "artist";
	private static final String COMPOSER = "composer";
	private static final String CLASSICAL = "classical";
	
	private Album.Builder albumBuilder;
	private AlbumStore albumStore;
	private Activity activity;
	
	public CreateEditAlbumPresentationModel(Activity activity, AlbumStore albumStore, long albumId)
	{
		initialize(activity, albumStore);
		
		if (albumIsNew(albumId))
			albumBuilder = new Album.Builder();
		else
		{
			Album album = albumStore.get(albumId);
			albumBuilder = album.createBuilder();
		}
	}
	
	private void initialize(Activity activity, AlbumStore albumStore)
	{
		this.activity = activity;
		this.albumStore = albumStore;
	}

	private boolean albumIsNew(long albumId)
	{
		return albumId == -1;
	}
	
	@DependsOn(CLASSICAL)
	public boolean isComposerEnabled()
	{
		return isClassical();
	}
	
	public void save()
	{
		albumStore.save(albumBuilder.create());
		activity.finish();
	}
	
	public String getTitle()
	{
		return albumBuilder.getTitle();
	}
	
	@CustomSetter
	public void setTitle(String title)
	{
		albumBuilder.setTitle(title);
		
		presentationModelChangeSupport.firePropertyChange(TITLE);
	}

	public String getArtist()
	{
		return albumBuilder.getArtist();
	}

	public void setArtist(String artist)
	{
		albumBuilder.setArtist(artist);
		
		presentationModelChangeSupport.firePropertyChange(ARTIST);
	}

	public boolean isClassical()
	{
		return albumBuilder.isClassical();
	}

	public void setClassical(boolean classical)
	{
		albumBuilder.setClassical(classical);
		
		presentationModelChangeSupport.firePropertyChange(CLASSICAL);
	}

	public String getComposer()
	{
		return albumBuilder.getComposer();
	}

	public void setComposer(String composer)
	{
		albumBuilder.setComposer(composer);
		
		presentationModelChangeSupport.firePropertyChange(COMPOSER);
	}

	@DependsOn(CLASSICAL)
	public String getWindowTitle()
	{
		if(albumBuilder.isNew())
			return activity.getString(R.string.create_album);
		
		return isClassical() ? "Edit Classical Album" : "Edit Album";
	}
	
	@PresentationModelRefresh
	public void refresh()
	{
		presentationModelChangeSupport.fireChangeAll();
	}
}
