package org.robobinding.albumsample.activity;

import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactory;

import android.app.Activity;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractActivity extends Activity {
    public void initializeContentView(int layoutId, Object presentationModel) {
	ViewBinder viewBinder = createViewBinder();
	View rootView = viewBinder.inflateAndBind(layoutId, presentationModel);
	setContentView(rootView);
    }
    
    private ViewBinder createViewBinder() {
	BinderFactory binderFactory = getAlbumApp().getReusableBinderFactory();
	return binderFactory.createViewBinder(this);
    }
    
    private AlbumApp getAlbumApp() {
	return (AlbumApp)getApplicationContext();
    }
}
