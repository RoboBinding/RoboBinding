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
public class OnFocusAttributeTest
		extends
		AbstractEventViewAttributeWithViewListenersAwareTest<View, OnFocusChangeAttribute, MockViewListenersForView> {
	@Test
	public void givenBoundAttribute_whenApplyFocus_thenEventReceived() {
		bindAttribute();

		setViewFocus();

		assertEventReceived();
	}

	private void setViewFocus() {
		ShadowView shadowView = Robolectric.shadowOf(view);
		shadowView.setViewFocus(true);
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
