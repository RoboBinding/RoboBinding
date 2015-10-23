package org.robobinding.widgetaddon.ratingbar;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class RatingBarAddOnTest {
	@Test
	public void shouldSupportMultipleOnRatingBarChangeListenersInRatingBar() {
		RatingBar ratingBar = new RatingBar(RuntimeEnvironment.application);
		RatingBarAddOn ratingBarAddOn = new RatingBarAddOn(ratingBar);

		MockOnRatingBarChangeListener listener1 = new MockOnRatingBarChangeListener();
		MockOnRatingBarChangeListener listener2 = new MockOnRatingBarChangeListener();

		ratingBarAddOn.addOnRatingBarChangeListener(listener1);
		ratingBarAddOn.addOnRatingBarChangeListener(listener2);

		ratingBar.setRating(RandomValues.anyFloat());

		assertTrue(listener1.ratingBarEventFired);
		assertTrue(listener2.ratingBarEventFired);
	}

	private static class MockOnRatingBarChangeListener implements OnRatingBarChangeListener {
		private boolean ratingBarEventFired;

		@Override
		public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
			ratingBarEventFired = true;
		}

	}
}
