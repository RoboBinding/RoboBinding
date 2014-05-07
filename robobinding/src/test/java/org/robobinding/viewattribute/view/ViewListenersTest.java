package org.robobinding.viewattribute.view;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;
import android.view.View.OnFocusChangeListener;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class ViewListenersTest {
    @Test
    public void shouldSupportMultipleOnFocusChangeListeners() {
	View view = new View(null);
	ViewListeners viewListeners = new ViewListeners(view);

	MockOnFocusChangeListener listener1 = new MockOnFocusChangeListener();
	MockOnFocusChangeListener listener2 = new MockOnFocusChangeListener();

	viewListeners.addOnFocusChangeListener(listener1);
	viewListeners.addOnFocusChangeListener(listener2);

	ShadowView shadowView = (ShadowView) Robolectric.shadowOf_(view);
	shadowView.setViewFocus(!view.isFocused());

	assertTrue(listener1.focusChangeEventFired);
	assertTrue(listener2.focusChangeEventFired);
    }

    private static class MockOnFocusChangeListener implements OnFocusChangeListener {
	private boolean focusChangeEventFired;

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
	    focusChangeEventFired = true;
	}
    }
}
