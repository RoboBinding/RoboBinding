package org.robobinding.widget.ratingbar;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class MockRatingBarListeners extends RatingBarListeners {
	public boolean addOnRatingBarChangeListenerInvoked;

	public MockRatingBarListeners(RatingBar ratingBar) {
		super(ratingBar);
	}

	@Override
	public void addOnRatingBarChangeListener(OnRatingBarChangeListener listener) {
		ratingBar.setOnRatingBarChangeListener(listener);
		addOnRatingBarChangeListenerInvoked = true;
	}
}
