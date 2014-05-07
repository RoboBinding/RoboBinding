package org.robobinding.albumsample.presentationmodel;

import org.robobinding.albumsample.activity.ViewAlbumsActivity;
import org.robobinding.presentationmodel.PresentationModel;

import android.content.Context;
import android.content.Intent;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@PresentationModel
public class HomePresentationModel {
	private Context context;

	public HomePresentationModel(Context context) {
		this.context = context;
	}

	public void albums() {
		context.startActivity(new Intent(context, ViewAlbumsActivity.class));
	}

}
