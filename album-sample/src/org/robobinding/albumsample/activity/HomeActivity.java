package org.robobinding.albumsample.activity;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.presentationmodel.HomePresentationModel;

import android.os.Bundle;

/**
 *
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class HomeActivity extends AbstractActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		HomePresentationModel presentationModel = new HomePresentationModel(
				this);
		initializeContentView(R.layout.activity_home, presentationModel);
	}
}
