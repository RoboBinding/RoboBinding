package org.robobinding.albumsampletest;

import org.robobinding.albumsample.activity.AlbumApp;
import org.robobinding.albumsample.activity.HomeActivity;
import org.robobinding.albumsample.store.AlbumStore;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractSampleAppTest extends ActivityInstrumentationTestCase2<HomeActivity> {
    protected Solo solo;
    protected AlbumStore albumStore;

    public AbstractSampleAppTest() {
	super(HomeActivity.class.getPackage().getName(), HomeActivity.class);
    }

    protected void setUp() throws Exception {
	super.setUp();
	solo = new Solo(getInstrumentation(), getActivity());
	albumStore = getAlbumApp().getAlbumStore();
    }

    private AlbumApp getAlbumApp() {
        return (AlbumApp)getActivity().getApplicationContext();
    }

    protected void tearDown() throws Exception {
	super.tearDown();
	solo.finishOpenedActivities();
    }
    
    protected void clickOnButtonWithLabel(String label) {
	solo.clickOnButton(label);
    }

    protected void clickOnButtonWithLabel(int resId) {
	clickOnButtonWithLabel(getString(resId));
    }

    protected String getString(int resId) {
	return getActivity().getString(resId);
    }
}
