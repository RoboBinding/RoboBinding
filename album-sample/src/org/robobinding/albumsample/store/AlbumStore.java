package org.robobinding.albumsample.store;

import java.util.List;

import org.robobinding.albumsample.model.Album;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public interface AlbumStore {
    Album get(long albumId);

    Album getByIndex(int position);

    List<Album> getAll();

    void save(Album album);

    void delete(Album album);

    void clear();
}
