package org.robobinding.widgetaddon.view;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.widget.view.OnTouchAttributeTest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
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
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class ViewAddOnForViewTest {
	private View view;
	private ViewAddOnForView viewAddOn;
	
	@Before
	public void setUp() {
		view = new View(RuntimeEnvironment.application);
		viewAddOn = new ViewAddOnForView(view);
	}
	
	@Test
	public void shouldSupportMultipleOnClickListeners() {
		MockOnClickListener listener1 = new MockOnClickListener();
		MockOnClickListener listener2 = new MockOnClickListener();

		viewAddOn.addOnClickListener(listener1);
		viewAddOn.addOnClickListener(listener2);

		view.performClick();

		assertTrue(listener1.clickEventFired);
		assertTrue(listener2.clickEventFired);
	}

	@Test
	public void shouldSupportMultipleOnLongClickListeners() {
		MockOnLongClickListener listener1 = new MockOnLongClickListener();
		boolean alreadyHandled = true;
		MockOnLongClickListener listener2 = new MockOnLongClickListener(alreadyHandled);

		viewAddOn.addOnLongClickListener(listener1);
		viewAddOn.addOnLongClickListener(listener2);

		view.performLongClick();

		assertTrue(listener1.longClickEventFired);
		assertTrue(listener2.longClickEventFired);
	}

	@Test
	public void shouldSupportMultipleOnFocusChangeListeners() {
		MockOnFocusChangeListener listener1 = new MockOnFocusChangeListener();
		MockOnFocusChangeListener listener2 = new MockOnFocusChangeListener();

		viewAddOn.addOnFocusChangeListener(listener1);
		viewAddOn.addOnFocusChangeListener(listener2);

		ShadowView shadowView = Shadows.shadowOf(view);
		shadowView.setViewFocus(!view.isFocused());

		assertTrue(listener1.focusChangeEventFired);
		assertTrue(listener2.focusChangeEventFired);
	}

	@Test
	public void shouldSupportMultipleOnTouchListeners() {
		MockOnTouchListener listener1 = new MockOnTouchListener();
		MockOnTouchListener listener2 = new MockOnTouchListener();

		viewAddOn.addOnTouchListener(listener1);
		viewAddOn.addOnTouchListener(listener2);

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
		private boolean handled;
		public MockOnLongClickListener(boolean handled) {
			this.handled = handled;
		}
		
		public MockOnLongClickListener() {
			this(false);
		}
		
		@Override
		public boolean onLongClick(View v) {
			longClickEventFired = true;
			return handled;
		}
	}

	private static class MockOnFocusChangeListener implements OnFocusChangeListener {
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
