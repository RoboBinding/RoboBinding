package org.robobinding.albumsample.presentationmodel;

import org.robobinding.aspects.PresentationModel;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@PresentationModel
public class HomePresentationModel {
    private final HomeView view;

    public HomePresentationModel(HomeView view) {
	this.view = view;
    }

    public void albums() {
	view.showAlbums();
    }
}
