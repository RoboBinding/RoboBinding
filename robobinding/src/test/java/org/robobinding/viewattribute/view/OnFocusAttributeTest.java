package org.robobinding.viewattribute.view;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

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
public class OnFocusAttributeTest extends AbstractCommandViewAttributeWithViewListenersAwareTest<View, OnFocusChangeAttribute, MockViewListeners> {
    @Test
    public void givenBoundAttribute_whenApplyFocus_thenEventReceived() {
	bindAttribute();

	setViewFocus();

	assertEventReceived();
    }

    @Test
    public void whenBinding_thenRegisterWithViewListeners() {
	bindAttribute();

	assertTrue(viewListeners.addOnFocusChangeListenerInvoked);
    }

    private void setViewFocus() {
	ShadowView shadowView = Robolectric.shadowOf(view);
	shadowView.setViewFocus(true);
    }

    private void assertEventReceived() {
	assertEventReceived(AbstractViewEvent.class);
    }

}
