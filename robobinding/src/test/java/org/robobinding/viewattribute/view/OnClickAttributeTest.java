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
public class OnClickAttributeTest extends AbstractCommandViewAttributeTest<View, OnClickAttribute> {
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
}
