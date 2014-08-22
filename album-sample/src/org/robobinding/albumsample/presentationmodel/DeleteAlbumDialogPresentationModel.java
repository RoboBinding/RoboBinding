package org.robobinding.albumsample.presentationmodel;

import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.store.AlbumStore;
import org.robobinding.aspects.PresentationModel;

import android.app.Dialog;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@PresentationModel
public class DeleteAlbumDialogPresentationModel{
	private final Dialog dialog;
	private final Album album;

	public DeleteAlbumDialogPresentationModel(Dialog dialog, Album album) {
		this.dialog = dialog;
		this.album = album;
	}

	public void deleteAlbum() {
		AlbumStore.delete(album);
		dialog.cancel();
	}

	public void dismissDialog() {
		dialog.dismiss();
	}

	public String getAlbumTitle() {
		return album.getTitle();
	}

	public String getAlbumArtist() {
		return album.getArtist();
	}
}
