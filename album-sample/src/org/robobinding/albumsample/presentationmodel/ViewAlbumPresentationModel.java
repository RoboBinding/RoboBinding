package org.robobinding.albumsample.presentationmodel;

import org.robobinding.albumsample.model.Album;
import org.robobinding.aspects.PresentationModel;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@PresentationModel
public class ViewAlbumPresentationModel {
    private final ViewAlbumView view;
    private final Album album;

    public ViewAlbumPresentationModel(ViewAlbumView view, Album album) {
	this.view = view;
	this.album = album;
    }

    public String getTitle() {
	return album.getTitle();
    }

    public String getArtist() {
	return album.getArtist();
    }

    public String getComposer() {
	return album.getComposer();
    }

    public boolean isComposerEnabled() {
	return album.isClassical();
    }

    public String getClassicalDescription() {
	return album.isClassical() ? "Classical" : "Not classical";
    }

    public void editAlbum() {
	view.editAlbum(album.getId());
    }

    public void deleteAlbum() {
	view.deleteAlbum(album);
    }
}
