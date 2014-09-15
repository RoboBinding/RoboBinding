package org.robobinding.widget.view;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.widget.AbstractEventViewAttributeWithViewListenersAwareTest;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest=Config.NONE)
public class OnClickAttributeTest
		extends
		AbstractEventViewAttributeWithViewListenersAwareTest<View, OnClickAttribute, MockViewListenersForView> {
	@Test
	public void givenBoundAttribute_whenClickingOnView_thenEventReceived() {
		bindAttribute();

		clickOnView();

		assertEventReceived();
	}

	private void clickOnView() {
		view.performClick();
	}

	private void assertEventReceived() {
		assertEventReceived(ClickEvent.class);
		ClickEvent clickEvent = getEventReceived();
		assertTrue(clickEvent.getView() == view);
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		bindAttribute();

		assertTrue(viewListeners.addOnClickListenerInvoked);
	}
}
