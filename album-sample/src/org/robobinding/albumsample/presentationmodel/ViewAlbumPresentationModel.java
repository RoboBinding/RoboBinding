package org.robobinding.albumsample.presentationmodel;

import org.robobinding.albumsample.activity.CreateEditAlbumActivity;
import org.robobinding.albumsample.activity.DeleteAlbumDialog;
import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.store.AlbumStore;
import org.robobinding.presentationmodel.PresentationModel;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@PresentationModel
public class ViewAlbumPresentationModel {
	private final Activity activity;
	private final long albumId;
	private Album album;

	public ViewAlbumPresentationModel(Activity activity, long albumId) {
		this.activity = activity;

		if (albumId == Album.NO_ID) {
			throw new IllegalArgumentException("No album id is given");
		}
		this.albumId = albumId;
	}

	public String getTitle() {
		return album.getTitle();
	}

	public String getArtist() {
		return album.getArtist();
	}

	public String getComposer() {
		return album.getComposer();
	}

	public boolean isComposerEnabled() {
		return album.isClassical();
	}

	public String getClassicalDescription() {
		return album.isClassical() ? "Classical" : "Not classical";
	}

	public void editAlbum() {
		Intent intent = new Intent(activity, CreateEditAlbumActivity.class);
		intent.putExtra(CreateEditAlbumActivity.ALBUM_ID, album.getId());
		activity.startActivity(intent);
	}

	public void deleteAlbum() {
		DeleteAlbumDialog deleteAlbumDialog = new DeleteAlbumDialog(activity,
				album);
		deleteAlbumDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				activity.finish();
			}
		});
		deleteAlbumDialog.show();
	}

	public void refresh() {
		this.album = AlbumStore.get(albumId);
		refreshPresentationModel();
	}
}
