package org.robobinding.albumsample.presentationmodel;

import java.util.ArrayList;
import java.util.List;

import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.store.AlbumStore;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.aspects.PresentationModel;
import org.robobinding.widget.adapterview.ItemClickEvent;

import android.util.Log;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
@PresentationModel
public class ViewAlbumsPresentationModel {

    private final ViewAlbumsView view;
    private final AlbumStore albumStore;

    public ViewAlbumsPresentationModel(ViewAlbumsView view, AlbumStore albumStore) {
	this.view = view;
	this.albumStore = albumStore;
    }

    @ItemPresentationModel(AlbumItemPresentationModel.class)
    public List<Album> getAlbums() {
	Log.d(ViewAlbumsPresentationModel.class.getSimpleName(), "in getAlbums():"+albumStore.getAll().size()+" albums");
	return new ArrayList<Album>(albumStore.getAll());
    }

    public void refreshAlbums() {
	firePropertyChange("albums");
    }

    public void createAlbum() {
	view.createAlbum();
    }

    public void viewAlbum(ItemClickEvent event) {
	viewAlbum(event.getPosition());
    }

    private void viewAlbum(int selectedAlbumPosition) {
	Album album = albumStore.getByIndex(selectedAlbumPosition);
	view.viewAlbum(album.getId());
    }
}
