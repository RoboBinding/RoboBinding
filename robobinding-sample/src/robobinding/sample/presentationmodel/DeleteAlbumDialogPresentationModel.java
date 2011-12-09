/**
 * Copyright 2011 Cheng Wei and Robert Taylor
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
import robobinding.sample.R;
import robobinding.sample.model.Album;
import robobinding.sample.store.AlbumStore;
import android.app.Dialog;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@PresentationModel
public class DeleteAlbumDialogPresentationModel
{
	private final Dialog dialog;
	private final Album album;

	public DeleteAlbumDialogPresentationModel(Dialog dialog, Album album)
	{
		this.dialog = dialog;
		this.dialog.setTitle("Title goes here");
		this.album = album;
	}
	
	public void deleteAlbum()
	{
		AlbumStore.delete(album);
		dialog.dismiss();
	}
	
	public void cancelDialog()
	{
		dialog.cancel();
	}
	
	public String getAlbumTitle()
	{
		return album.getTitle();
	}
	
	public String getAlbumArtist()
	{
		return album.getArtist();
	}
	
	public String getTitle()
	{
		return dialog.getContext().getString(R.string.delete_album);
	}
}
