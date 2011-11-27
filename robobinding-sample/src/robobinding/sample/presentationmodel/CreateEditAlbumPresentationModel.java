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

import robobinding.DependsOn;
import robobinding.NotifyPropertyChange;
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
public class CreateEditAlbumPresentationModel
{
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
		return albumId == Album.NO_ID;
	}
	
	@DependsOn(CLASSICAL)
	public boolean isComposerEnabled()
	{
		return isClassical();
	}
	
	@SuppressWarnings("static-access")
	public void save()
	{
		albumStore.save(albumBuilder.create());
		activity.finish();
	}
	
	public String getTitle()
	{
		return albumBuilder.getTitle();
	}
	
	public void setTitle(String title)
	{
		albumBuilder.setTitle(title);
	}

	public String getArtist()
	{
		return albumBuilder.getArtist();
	}

	public void setArtist(String artist)
	{
		albumBuilder.setArtist(artist);
	}

	public boolean isClassical()
	{
		return albumBuilder.isClassical();
	}

	public void setClassical(boolean classical)
	{
		albumBuilder.setClassical(classical);
	}

	public String getComposer()
	{
		return albumBuilder.getComposer();
	}

	public void setComposer(String composer)
	{
		albumBuilder.setComposer(composer);
	}

	@DependsOn(CLASSICAL)
	public String getWindowTitle()
	{
		if(albumBuilder.isNew())
			return activity.getString(R.string.create_album);
		
		return isClassical() ? "Edit Classical Album" : "Edit Album";
	}
}
