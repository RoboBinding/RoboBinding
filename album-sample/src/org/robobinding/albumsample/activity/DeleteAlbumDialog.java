package org.robobinding.albumsample.activity;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.presentationmodel.DeleteAlbumDialogPresentationModel;
import org.robobinding.binder.Binders;

import android.app.Dialog;
import android.content.Context;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class DeleteAlbumDialog extends Dialog {
	public DeleteAlbumDialog(Context context, Album album) {
		super(context);
		setCancelable(true);

		DeleteAlbumDialogPresentationModel deleteAlbumDialogPresentationModel = new DeleteAlbumDialogPresentationModel(
				this, album);
		Binders.bind(this, R.layout.dialog_delete_album,
				deleteAlbumDialogPresentationModel);
	}
}
