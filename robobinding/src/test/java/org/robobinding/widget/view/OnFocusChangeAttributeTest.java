package org.robobinding.widget.view;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.event.AbstractEventViewAttributeWithViewListenersAwareTest;

import android.view.View;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class OnFocusChangeAttributeTest extends
	AbstractEventViewAttributeWithViewListenersAwareTest<View, OnFocusChangeAttribute, MockViewListeners> {
    @Test
    public void givenBoundAttribute_whenChangeFocus_thenEventReceived() {
	bindAttribute();

	changeViewFocus();

	assertEventReceived();
    }

    private void changeViewFocus() {
        ShadowView shadowView = Robolectric.shadowOf(view);
        shadowView.setViewFocus(RandomValues.trueOrFalse());
    }

    private void assertEventReceived() {
        assertEventReceived(AbstractViewEvent.class);
    }

    @Test
    public void whenBinding_thenRegisterWithViewListeners() {
	bindAttribute();

	assertTrue(viewListeners.addOnFocusChangeListenerInvoked);
    }
}
