package org.robobinding.albumsample.presentationmodel;

import org.robobinding.albumsample.model.Album;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface ViewAlbumView {

    void editAlbum(long albumId);

    void deleteAlbum(Album album);

}
