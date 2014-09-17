package org.robobinding.widget.ratingbar;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.widget.RatingBar;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractRatingBarAttributeTest {
	protected RatingBar view;
	protected MockRatingBarListeners viewListeners;

	@Before
	public void initializeViewAndListeners() {
		view = new RatingBar(Robolectric.application);
		viewListeners = new MockRatingBarListeners(view);
	}

	public <T extends ViewListenersAware<RatingBarListeners>> T withListenersSet(T attribute) {
		attribute.setViewListeners(viewListeners);
		return attribute;
	}
}
