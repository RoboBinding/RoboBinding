package org.robobinding.widget.listview;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.robobinding.widget.AbstractEventViewAttributeWithViewListenersAwareTest;

import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnScrollStateChangedAttributeTest 
	extends AbstractEventViewAttributeWithViewListenersAwareTest<ListView, OnScrollStateChangedAttribute, MockListViewListeners> {
    @Test@Ignore
    public void givenBoundAttribute_whenChangeScrollState_thenEventReceived() {
	bindAttribute();

	changeScrollState();

	assertEventReceived();
    }

    private void changeScrollState() {
        view.smoothScrollToPositionFromTop(1, 0, 500);
    }

    private void assertEventReceived() {
        assertEventReceived(ScrollStateChangedEvent.class);
    }

    @Test
    public void whenBinding_thenRegisterWithViewListeners() {
	bindAttribute();

	assertTrue(viewListeners.addOnScrollListenerInvoked);
    }

}
