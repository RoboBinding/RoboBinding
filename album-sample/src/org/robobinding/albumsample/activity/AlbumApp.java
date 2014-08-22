package org.robobinding.albumsample.activity;

import org.robobinding.albumsample.store.AlbumStore;
import org.robobinding.albumsample.store.MemoryAlbumStore;
import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;

import android.app.Application;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class AlbumApp extends Application {
    private BinderFactory reusableBinderFactory;
    private AlbumStore albumStore;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        reusableBinderFactory = new BinderFactoryBuilder().build();
        albumStore = new MemoryAlbumStore();
        
        TestData testData = new TestData();
        testData.setUp(albumStore);
    }

    public BinderFactory getReusableBinderFactory() {
        return reusableBinderFactory;
    }

    public AlbumStore getAlbumStore() {
        return albumStore;
    }
}
