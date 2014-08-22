package org.robobinding.albumsample.presentationmodel;

import java.util.ArrayList;
import java.util.List;

import org.robobinding.albumsample.activity.CreateEditAlbumActivity;
import org.robobinding.albumsample.activity.ViewAlbumActivity;
import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.store.AlbumStore;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.aspects.PresentationModel;
import org.robobinding.widget.adapterview.ItemClickEvent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@PresentationModel
public class ViewAlbumsPresentationModel {

    private final Context context;

    public ViewAlbumsPresentationModel(Activity activity) {
	this.context = activity;
    }

    @ItemPresentationModel(AlbumItemPresentationModel.class)
    public List<Album> getAlbums() {
	Log.d(ViewAlbumsPresentationModel.class.getSimpleName(), "in getAlbums():"+AlbumStore.getAll().size()+" albums");
	return new ArrayList<Album>(AlbumStore.getAll());
    }

    public void refreshAlbums() {
	firePropertyChange("albums");
    }

    public void createAlbum() {
	context.startActivity(new Intent(context, CreateEditAlbumActivity.class));
    }

    public void viewAlbum(ItemClickEvent event) {
	viewAlbum(event.getPosition());
    }

    private void viewAlbum(int selectedAlbumPosition) {
	Intent intent = new Intent(context, ViewAlbumActivity.class);
	intent.putExtra(ViewAlbumActivity.ALBUM_ID, AlbumStore.getByIndex(selectedAlbumPosition).getId());
	context.startActivity(intent);
    }
}
