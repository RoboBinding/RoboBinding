package org.robobinding.albumsample.activity;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.presentationmodel.ViewAlbumsPresentationModel;

import android.os.Bundle;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewAlbumsActivity extends AbstractActivity {
    protected ViewAlbumsPresentationModel presentationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	presentationModel = new ViewAlbumsPresentationModel(this);
	initializeContentView(R.layout.activity_view_albums, presentationModel);
    }

    @Override
    protected void onResume() {
	super.onResume();
	presentationModel.refreshAlbums();
    }
}