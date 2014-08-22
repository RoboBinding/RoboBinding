package org.robobinding.albumsample.presentationmodel;

import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.store.AlbumStore;
import org.robobinding.annotation.DependsOnStateOf;
import org.robobinding.aspects.PresentationModel;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@PresentationModel
public class CreateEditAlbumPresentationModel {
    private static final String CLASSICAL = "classical";

    private final CreateEditAlbumView view;
    private final AlbumStore albumStore;
    private final Album.Builder albumBuilder;

    public CreateEditAlbumPresentationModel(CreateEditAlbumView view, AlbumStore albumStore, 
	    Album.Builder albumBuilder) {
	this.view = view;
	this.albumStore = albumStore;
	this.albumBuilder = albumBuilder;
    }

    public void save() {
	albumStore.save(albumBuilder.create());
	view.finishActivity();
    }

    public String getTitle() {
	return albumBuilder.getTitle();
    }

    public void setTitle(String title) {
	albumBuilder.setTitle(title);
    }

    public String getArtist() {
	return albumBuilder.getArtist();
    }

    public void setArtist(String artist) {
	albumBuilder.setArtist(artist);
    }

    public boolean isClassical() {
	return albumBuilder.isClassical();
    }

    public void setClassical(boolean classical) {
	albumBuilder.setClassical(classical);
    }

    @DependsOnStateOf(CLASSICAL)
    public boolean isComposerEnabled() {
	return isClassical();
    }

    public String getComposer() {
	return albumBuilder.getComposer();
    }

    public void setComposer(String composer) {
	albumBuilder.setComposer(composer);
    }

    @DependsOnStateOf(CLASSICAL)
    public String getWindowTitle() {
	if (albumBuilder.isNew())
	    return view.getCreateAlbumTitle();

	return isClassical() ? "Edit Classical Album" : "Edit Album";
    }
}
