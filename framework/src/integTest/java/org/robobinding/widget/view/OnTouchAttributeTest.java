package org.robobinding.widget.view;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.widget.AbstractEventViewAttributeWithViewListenersAwareTest;
import org.robolectric.annotation.Config;

import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest=Config.NONE)
public class OnTouchAttributeTest
		extends
		AbstractEventViewAttributeWithViewListenersAwareTest<View, OnTouchAttribute, MockViewListenersForView> {
	@Test
	public void givenBoundAttribute_whenClickingOnView_thenEventReceived() {
		bindAttribute();

		touchView();

		assertEventReceived();
	}

	private void touchView() {
		view.dispatchTouchEvent(anyMotionEvent());
	}

	public static MotionEvent anyMotionEvent() {
		return MotionEvent.obtain(System.currentTimeMillis(),
				System.currentTimeMillis() + 1000, MotionEvent.ACTION_DOWN, 5,
				5, 1);
	}

	private void assertEventReceived() {
		assertEventReceived(TouchEvent.class);
		TouchEvent event = getEventReceived();
		assertTrue(event.getView() == view);
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		bindAttribute();

		assertTrue(viewListeners.addOnTouchListenerInvoked);
	}
}
