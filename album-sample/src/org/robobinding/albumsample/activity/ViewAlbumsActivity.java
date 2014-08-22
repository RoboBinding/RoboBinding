package org.robobinding.albumsample.activity;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.presentationmodel.ViewAlbumsPresentationModel;
import org.robobinding.albumsample.presentationmodel.ViewAlbumsView;

import android.content.Intent;
import android.os.Bundle;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewAlbumsActivity extends AbstractActivity implements ViewAlbumsView {
    protected ViewAlbumsPresentationModel presentationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	presentationModel = new ViewAlbumsPresentationModel(this, getAlbumStore());
	initializeContentView(R.layout.activity_view_albums, presentationModel);
    }

    @Override
    protected void onResume() {
	super.onResume();
	presentationModel.refreshAlbums();
    }

    @Override
    public void createAlbum() {
	startActivity(new Intent(this, CreateEditAlbumActivity.class));
    }

    @Override
    public void viewAlbum(long albumId) {
	Intent intent = new Intent(this, ViewAlbumActivity.class);
	intent.putExtra(ViewAlbumActivity.ALBUM_ID, albumId);
	startActivity(intent);
    }
}