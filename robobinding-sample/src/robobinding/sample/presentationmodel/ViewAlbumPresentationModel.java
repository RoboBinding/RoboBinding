/**
 * ViewAlbumPresentationModel.java
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

import robobinding.presentationmodel.AbstractPresentationModel;
import robobinding.presentationmodel.PresentationModelRefresh;
import robobinding.sample.CreateEditAlbumActivity;
import robobinding.sample.model.Album;
import robobinding.sample.store.AlbumStore;
import android.content.Context;
import android.content.Intent;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class ViewAlbumPresentationModel extends AbstractPresentationModel
{
	private final Context context;
	private final AlbumStore albumStore;
	private final long albumId;
	private Album album;
	
	public ViewAlbumPresentationModel(Context context, AlbumStore albumStore, long albumId)
	{
		this.context = context;
		this.albumStore = albumStore;
		this.albumId = albumId;
	}

	public String getTitle()
	{
		return album.getTitle();
	}
	
	public String getArtist()
	{
		return album.getArtist();
	}
	
	public String getComposer()
	{
		return album.getComposer();
	}
	
	public boolean isComposerEnabled()
	{
		return album.isClassical();
	}
	
	public String getClassicalDescription()
	{
		return album.isClassical() ? "Classical" : "Not classical";
	}
	
	public void editAlbum()
	{
		Intent intent = new Intent(context, CreateEditAlbumActivity.class);
		intent.putExtra(CreateEditAlbumActivity.ALBUM_ID, album.getId());
		context.startActivity(intent);
	}

	@PresentationModelRefresh
	public void refresh()
	{
		this.album = albumStore.get(albumId);
		presentationModelChangeSupport.fireChangeAll();
	}

}
