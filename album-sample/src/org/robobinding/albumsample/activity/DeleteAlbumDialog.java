package org.robobinding.albumsample.activity;

import org.robobinding.ViewBinder;
import org.robobinding.albumsample.R;
import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.presentationmodel.DeleteAlbumPresentationModel;
import org.robobinding.albumsample.presentationmodel.DeleteAlbumView;
import org.robobinding.binder.BinderFactory;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class DeleteAlbumDialog extends Dialog implements DeleteAlbumView {
    private final Activity activity;
    
    public DeleteAlbumDialog(Activity activity, Album album) {
	super(activity);
	this.activity = activity;
	setCancelable(true);
	setOnCancelListener(new OnCancelListener() {
	    @Override
	    public void onCancel(DialogInterface dialog) {
		navigateToAlbums();
	    }
	});

	DeleteAlbumPresentationModel deleteAlbumDialogPresentationModel = new DeleteAlbumPresentationModel(
		this, getAlbumApp().getAlbumStore(), album);
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

    @Override
    public void deleted() {
	navigateToAlbums();
    }

    private void navigateToAlbums() {
	dismiss();
	activity.finish();
    }

    @Override
    public void cancelOperation() {
	dismiss();
    }

}
