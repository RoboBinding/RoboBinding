package org.robobinding.albumsample.activity;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.presentationmodel.HomePresentationModel;
import org.robobinding.binder.Binders;

import android.app.Activity;
import android.os.Bundle;

/**
 *
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class HomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		HomePresentationModel presentationModel = new HomePresentationModel(
				this);
		Binders.bind(this, R.layout.home_activity, presentationModel);
	}
}
