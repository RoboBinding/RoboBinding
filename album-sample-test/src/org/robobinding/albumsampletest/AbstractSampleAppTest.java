package org.robobinding.albumsampletest;

import org.robobinding.albumsample.activity.HomeActivity;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractSampleAppTest extends ActivityInstrumentationTestCase2<HomeActivity> {
    protected Solo solo;

    public AbstractSampleAppTest() {
	super("org.robobinding.albumsample.activity", HomeActivity.class);
    }

    protected void setUp() throws Exception {
	super.setUp();
	solo = new Solo(getInstrumentation(), getActivity());
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
