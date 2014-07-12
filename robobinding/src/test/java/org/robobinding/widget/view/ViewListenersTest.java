package org.robobinding.widget.view;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;

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
    public void shouldSupportMultipleOnClickListeners() {
	View view = new View(null);
	ViewListeners viewListeners = new ViewListeners(view);

	MockOnClickListener listener1 = new MockOnClickListener();
	MockOnClickListener listener2 = new MockOnClickListener();

	viewListeners.addOnClickListener(listener1);
	viewListeners.addOnClickListener(listener2);

	view.performClick();

	assertTrue(listener1.clickEventFired);
	assertTrue(listener2.clickEventFired);
    }

    @Test
    public void shouldSupportMultipleOnLongClickListeners() {
	View view = new View(null);
	ViewListeners viewListeners = new ViewListeners(view);

	MockOnLongClickListener listener1 = new MockOnLongClickListener();
	MockOnLongClickListener listener2 = new MockOnLongClickListener();

	viewListeners.addOnLongClickListener(listener1);
	viewListeners.addOnLongClickListener(listener2);

	view.performLongClick();

	assertTrue(listener1.longClickEventFired);
	assertTrue(listener2.longClickEventFired);
    }

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

    private static class MockOnClickListener implements OnClickListener {
	private boolean clickEventFired;

	@Override
	public void onClick(View v) {
	    clickEventFired = true;
	}
    }

    private static class MockOnLongClickListener implements OnLongClickListener {
	private boolean longClickEventFired;

	@Override
	public boolean onLongClick(View v) {
	    longClickEventFired = true;
	    return false;
	}
    }

    private static class MockOnFocusChangeListener implements OnFocusChangeListener {
	private boolean focusChangeEventFired;

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
	    focusChangeEventFired = true;
	}
    }
}
