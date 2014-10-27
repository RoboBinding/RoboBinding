package org.robobinding.widget.ratingbar;

import org.robobinding.widget.view.ViewListenersForView;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class RatingBarListeners extends ViewListenersForView {
	final RatingBar ratingBar;
	private OnRatingBarChangeListeners onRatingBarChangeListeners;

	public RatingBarListeners(RatingBar ratingBar) {
		super(ratingBar);
		this.ratingBar = ratingBar;
	}

	public void addOnRatingBarChangeListener(OnRatingBarChangeListener listener) {
		ensureOnRatingBarChangeListenersInitialized();
		onRatingBarChangeListeners.addListener(listener);
	}

	private void ensureOnRatingBarChangeListenersInitialized() {
		if (onRatingBarChangeListeners == null) {
			onRatingBarChangeListeners = new OnRatingBarChangeListeners();
			ratingBar.setOnRatingBarChangeListener(onRatingBarChangeListeners);
		}
	}
}
