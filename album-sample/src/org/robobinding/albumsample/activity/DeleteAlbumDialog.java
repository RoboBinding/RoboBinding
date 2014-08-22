package org.robobinding.albumsample.activity;

import org.robobinding.ViewBinder;
import org.robobinding.albumsample.R;
import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.presentationmodel.DeleteAlbumDialogPresentationModel;
import org.robobinding.binder.BinderFactory;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

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

	DeleteAlbumDialogPresentationModel deleteAlbumDialogPresentationModel = new DeleteAlbumDialogPresentationModel(this, album);
	setTitle(R.string.delete_album);
	initializeContentView(R.layout.dialog_delete_album, deleteAlbumDialogPresentationModel);
    }

    private void initializeContentView(int layoutId, Object presentationModel) {
	BinderFactory binderFactory = getAlbumApp().getReusableBinderFactory();
	ViewBinder viewBinder = binderFactory.createViewBinder(getContext());
	View rootView = viewBinder.inflateAndBind(layoutId, presentationModel);
	setContentView(rootView);
    }

    private AlbumApp getAlbumApp() {
	return (AlbumApp) getContext().getApplicationContext();
    }

}
