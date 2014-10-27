package org.robobinding.widget.ratingbar;

import org.robobinding.viewattribute.AbstractListeners;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnRatingBarChangeListeners extends AbstractListeners<OnRatingBarChangeListener> implements OnRatingBarChangeListener {
	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		for (OnRatingBarChangeListener listener : listeners) {
			listener.onRatingChanged(ratingBar, rating, fromUser);
		}
	}

}
