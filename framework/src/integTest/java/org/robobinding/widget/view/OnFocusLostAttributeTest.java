package org.robobinding.widget.view;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.widget.AbstractEventViewAttributeWithViewListenersAwareTest;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowView;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
public class OnFocusLostAttributeTest
		extends
		AbstractEventViewAttributeWithViewListenersAwareTest<View, OnFocusChangeAttribute, MockViewListenersForView> {
	@Test
	public void givenBoundAttribute_whenClearFocus_thenEventReceived() {
		bindAttribute();

		clearViewFocus();

		assertEventReceived();
	}

	private void clearViewFocus() {
		ShadowView shadowView = Robolectric.shadowOf(view);
		shadowView.setViewFocus(false);
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