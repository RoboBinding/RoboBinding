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
public class OnFocusLostAttributeTest extends AbstractCommandViewAttributeWithViewListenersAwareTest<View, 
	OnFocusChangeAttribute, MockViewListeners> {
    @Test
    public void givenBoundAttribute_whenClearFocus_thenEventReceived() {
	bindAttribute();

	clearViewFocus();

	assertEventReceived();
    }

    @Test
    public void whenBinding_thenRegisterWithViewListeners() {
	bindAttribute();

	assertTrue(viewListeners.addOnFocusChangeListenerInvoked);
    }

    private void clearViewFocus() {
	ShadowView shadowView = Robolectric.shadowOf(view);
	shadowView.setViewFocus(false);
    }

    private void assertEventReceived() {
	assertEventReceived(AbstractViewEvent.class);
    }
}