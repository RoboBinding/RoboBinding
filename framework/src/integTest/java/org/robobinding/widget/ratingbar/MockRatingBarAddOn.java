package org.robobinding.widget.ratingbar;

import org.robobinding.widgetaddon.ratingbar.RatingBarAddOn;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class MockRatingBarAddOn extends RatingBarAddOn {
	private final RatingBar ratingBar;
	public boolean addOnRatingBarChangeListenerInvoked;

	public MockRatingBarAddOn(RatingBar ratingBar) {
		super(null);
		this.ratingBar = ratingBar;
	}

	@Override
	public void addOnRatingBarChangeListener(OnRatingBarChangeListener listener) {
		ratingBar.setOnRatingBarChangeListener(listener);
		addOnRatingBarChangeListenerInvoked = true;
	}
}
