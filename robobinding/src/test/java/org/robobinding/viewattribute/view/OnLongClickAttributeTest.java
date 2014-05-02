package org.robobinding.viewattribute.view;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractCommandViewAttributeTest;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnLongClickAttributeTest extends AbstractCommandViewAttributeTest<View, OnLongClickAttribute> {
    @Test
    public void givenBoundAttribute_whenChangeChecked_thenEventReceived() {
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
}
