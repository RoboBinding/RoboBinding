package org.robobinding.widget.view;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.widget.EventCommand;
import org.robolectric.annotation.Config;

import android.view.MotionEvent;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class OnTouchAttributeTest extends AbstractViewEventAttributeTest {
	private OnTouchAttribute attribute;
	private EventCommand eventCommand;

	@Before
	public void setUp() {
		attribute = new OnTouchAttribute();
		eventCommand = new EventCommand();
	}

	@Test
	public void givenBoundAttribute_whenClickingOnView_thenEventReceived() {
		bindAttribute();

		touchView();

		assertEventReceived();
	}

	private void bindAttribute() {
		attribute.bind(viewAddOn, eventCommand, view);
	}

	private void touchView() {
		view.dispatchTouchEvent(anyMotionEvent());
	}

	public static MotionEvent anyMotionEvent() {
		return MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis() + 1000, MotionEvent.ACTION_DOWN, 5, 5, 1);
	}

	private void assertEventReceived() {
		eventCommand.assertEventReceived(TouchEvent.class);
		TouchEvent event = eventCommand.getEventReceived();
		assertTrue(event.getView() == view);
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		bindAttribute();

		assertTrue(viewAddOn.addOnTouchListenerInvoked);
	}
}
