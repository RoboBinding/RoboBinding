package org.robobinding.albumsample.activity;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.presentationmodel.CreateEditAlbumPresentationModel;
import org.robobinding.albumsample.presentationmodel.CreateEditAlbumView;

import android.os.Bundle;

/**
 *
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class CreateEditAlbumActivity extends AbstractActivity implements CreateEditAlbumView {

    public static final String ALBUM_ID = ViewAlbumActivity.ALBUM_ID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	long albumId = getIntent().getLongExtra(ALBUM_ID, Album.NO_ID);
	
	Album.Builder albumBuilder;
	if (Album.isNew(albumId))
		albumBuilder = new Album.Builder();
	else {
		Album album = getAlbumStore().get(albumId);
		albumBuilder = album.createBuilder();
	}


	CreateEditAlbumPresentationModel presentationModel = new CreateEditAlbumPresentationModel(this, getAlbumStore(), albumBuilder);
	initializeContentView(R.layout.activity_create_edit_album, presentationModel);
    }
    
    @Override
    public void finishActivity() {
        finish();
    }
   
    @Override
    public String getCreateAlbumTitle() {
        return getString(R.string.create_album);
    }
}