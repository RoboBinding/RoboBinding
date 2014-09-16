package org.robobinding.widget.view;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowView;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class ViewListenersForViewTest {
	@Test
	public void shouldSupportMultipleOnClickListeners() {
		View view = new View(Robolectric.application);
		ViewListenersForView viewListeners = new ViewListenersForView(view);

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
		View view = new View(Robolectric.application);
		ViewListenersForView viewListeners = new ViewListenersForView(view);

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
		View view = new View(Robolectric.application);
		ViewListenersForView viewListeners = new ViewListenersForView(view);

		MockOnFocusChangeListener listener1 = new MockOnFocusChangeListener();
		MockOnFocusChangeListener listener2 = new MockOnFocusChangeListener();

		viewListeners.addOnFocusChangeListener(listener1);
		viewListeners.addOnFocusChangeListener(listener2);

		ShadowView shadowView = (ShadowView) Robolectric.shadowOf_(view);
		shadowView.setViewFocus(!view.isFocused());

		assertTrue(listener1.focusChangeEventFired);
		assertTrue(listener2.focusChangeEventFired);
	}

	@Test
	public void shouldSupportMultipleOnTouchListeners() {
		View view = new View(Robolectric.application);
		ViewListenersForView viewListeners = new ViewListenersForView(view);

		MockOnTouchListener listener1 = new MockOnTouchListener();
		MockOnTouchListener listener2 = new MockOnTouchListener();

		viewListeners.addOnTouchListener(listener1);
		viewListeners.addOnTouchListener(listener2);

		view.dispatchTouchEvent(OnTouchAttributeTest.anyMotionEvent());

		assertTrue(listener1.touchEventFired);
		assertTrue(listener2.touchEventFired);
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

	private static class MockOnFocusChangeListener implements
			OnFocusChangeListener {
		private boolean focusChangeEventFired;

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			focusChangeEventFired = true;
		}
	}

	private static class MockOnTouchListener implements OnTouchListener {
		private boolean touchEventFired;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			touchEventFired = true;
			return false;
		}
	}
}
