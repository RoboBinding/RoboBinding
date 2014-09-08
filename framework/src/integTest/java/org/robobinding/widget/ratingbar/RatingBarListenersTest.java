package org.robobinding.widget.ratingbar;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.RandomValues;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class RatingBarListenersTest {
    @Test
    public void shouldSupportMultipleOnRatingBarChangeListenersInRatingBar() {
	RatingBar ratingBar = new RatingBar(null);
	RatingBarListeners ratingBarListeners = new RatingBarListeners(ratingBar);

	MockOnRatingBarChangeListener listener1 = new MockOnRatingBarChangeListener();
	MockOnRatingBarChangeListener listener2 = new MockOnRatingBarChangeListener();

	ratingBarListeners.addOnRatingBarChangeListener(listener1);
	ratingBarListeners.addOnRatingBarChangeListener(listener2);

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
