package org.robobinding.albumsample.presentationmodel;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.store.AlbumStore;
import org.robobinding.annotation.DependsOnStateOf;
import org.robobinding.aspects.PresentationModel;

import android.app.Activity;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@PresentationModel
public class CreateEditAlbumPresentationModel {
    private static final String CLASSICAL = "classical";

    private final Activity activity;
    private final Album.Builder albumBuilder;

    public CreateEditAlbumPresentationModel(Activity activity, Album.Builder albumBuilder) {
	this.activity = activity;
	this.albumBuilder = albumBuilder;
    }

    public void save() {
	AlbumStore.save(albumBuilder.create());
	activity.finish();
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
	    return activity.getString(R.string.create_album);

	return isClassical() ? "Edit Classical Album" : "Edit Album";
    }
}
