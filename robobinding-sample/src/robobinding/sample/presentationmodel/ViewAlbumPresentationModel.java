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
package robobinding.sample.presentationmodel;

import robobinding.presentationmodelaspects.PresentationModel;
import robobinding.sample.CreateEditAlbumActivity;
import robobinding.sample.DeleteAlbumDialog;
import robobinding.sample.model.Album;
import robobinding.sample.store.AlbumStore;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@PresentationModel
public class ViewAlbumPresentationModel
{
	private final Activity activity;
	private final long albumId;
	private Album album;
	
	public ViewAlbumPresentationModel(Activity activity, long albumId)
	{
		this.activity = activity;
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
		Intent intent = new Intent(activity, CreateEditAlbumActivity.class);
		intent.putExtra(CreateEditAlbumActivity.ALBUM_ID, album.getId());
		activity.startActivity(intent);
	}

	public void deleteAlbum()
	{
		DeleteAlbumDialog deleteAlbumDialog = new DeleteAlbumDialog(activity, album);
		deleteAlbumDialog.setOnDismissListener(new OnDismissListener(){
			@Override
			public void onDismiss(DialogInterface dialog)
			{
				activity.finish();
			}
		});
		deleteAlbumDialog.show();
	}
	
	public void refresh()
	{
		this.album = AlbumStore.get(albumId);
		refreshPresentationModel();
	}
}
