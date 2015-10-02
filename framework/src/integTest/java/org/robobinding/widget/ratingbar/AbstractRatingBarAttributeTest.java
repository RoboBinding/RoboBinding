package org.robobinding.widget.ratingbar;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

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
	protected MockRatingBarAddOn viewAddOn;

	@Before
	public void initializeViewAndListeners() {
		view = new RatingBar(RuntimeEnvironment.application);
		viewAddOn = new MockRatingBarAddOn(view);
	}
}
