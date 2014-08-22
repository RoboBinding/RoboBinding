package org.robobinding.albumsample.activity;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.presentationmodel.ViewAlbumPresentationModel;
import org.robobinding.albumsample.presentationmodel.ViewAlbumView;

import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class ViewAlbumActivity extends AbstractActivity implements ViewAlbumView {
    public static final String ALBUM_ID = "album_id";

    private ViewAlbumPresentationModel presentationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	Intent intent = getIntent();
	long albumId = intent.getLongExtra(ALBUM_ID, Album.NO_ID);

	if (albumId == Album.NO_ID) {
	    throw new IllegalArgumentException("No album id is given");
	}
	Album album = getAlbumStore().get(albumId);

	presentationModel = new ViewAlbumPresentationModel(this, album);
	initializeContentView(R.layout.activity_view_album, presentationModel);
    }

    @Override
    public void editAlbum(long albumId) {
	Intent intent = new Intent(this, CreateEditAlbumActivity.class);
	intent.putExtra(CreateEditAlbumActivity.ALBUM_ID, albumId);
	startActivity(intent);
    }

    @Override
    public void deleteAlbum(Album album) {
	DeleteAlbumDialog deleteAlbumDialog = new DeleteAlbumDialog(this, album);
	deleteAlbumDialog.show();
    }
}
