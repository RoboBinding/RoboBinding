package org.robobinding.albumsample.presentationmodel;

import org.robobinding.albumsample.model.Album;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
 * 
 * @since 1.0
 * @author Robert Taylor
 */
public class AlbumItemPresentationModel implements ItemPresentationModel<Album> {
    protected Album album;

    public String getTitle() {
	return album.getTitle();
    }

    public String getArtist() {
	return album.getArtist();
    }

    @Override
    public void updateData(int index, Album bean) {
	this.album = bean;
    }
}
