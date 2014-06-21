package org.robobinding.widget.view;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.viewattribute.event.AbstractEventViewAttributeWithViewListenersAwareTest;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnLongClickAttributeTest extends AbstractEventViewAttributeWithViewListenersAwareTest<View, OnLongClickAttribute, MockViewListeners> {
    @Test
    public void givenBoundAttribute_whenLongClickOnView_thenEventReceived() {
	bindAttribute();

	longClickOnView();

	assertEventReceived();
    }

    private void longClickOnView() {
	view.performLongClick();
    }

    private void assertEventReceived() {
	assertEventReceived(ClickEvent.class);
	ClickEvent clickEvent = getEventReceived();
	assertTrue(clickEvent.getView() == view);
    }

    @Test
    public void whenBinding_thenRegisterWithViewListeners() {
	bindAttribute();

	assertTrue(viewListeners.addOnLongClickListenerInvoked);
    }
}
