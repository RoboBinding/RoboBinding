package org.robobinding.albumsample.activity;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.presentationmodel.ViewAlbumPresentationModel;
import org.robobinding.albumsample.store.AlbumStore;

import android.content.Intent;
import android.os.Bundle;

/**
 *
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class ViewAlbumActivity extends AbstractActivity {
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
		Album album = AlbumStore.get(albumId);

		presentationModel = new ViewAlbumPresentationModel(this, album);
		initializeContentView(R.layout.activity_view_album, presentationModel);
	}
}
