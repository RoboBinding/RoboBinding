package org.robobinding.albumsample.activity;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.presentationmodel.CreateEditAlbumPresentationModel;
import org.robobinding.binder.Binders;

import android.app.Activity;
import android.os.Bundle;

/**
 *
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class CreateEditAlbumActivity extends Activity {

    public static final String ALBUM_ID = ViewAlbumActivity.ALBUM_ID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	long albumId = getIntent().getLongExtra(ALBUM_ID, Album.NO_ID);

	CreateEditAlbumPresentationModel presentationModel = new CreateEditAlbumPresentationModel(this, albumId);
	Binders.bind(this, R.layout.create_edit_album_activity, presentationModel);
    }
}