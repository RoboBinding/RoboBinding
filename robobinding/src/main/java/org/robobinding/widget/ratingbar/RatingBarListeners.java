package org.robobinding.widget.ratingbar;

import org.robobinding.widget.view.ViewListeners;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class RatingBarListeners extends ViewListeners {
    final RatingBar ratingBar;
    private OnRatingBarChangeListeners ratingBarChangeListeners;

    public RatingBarListeners(RatingBar ratingBar) {
	super(ratingBar);
	this.ratingBar = ratingBar;
    }

    public void addOnRatingBarChangeListener(OnRatingBarChangeListener listener) {
	ensureRatingBarChangeListenersInitialized();
	ratingBarChangeListeners.addListener(listener);
    }

    private void ensureRatingBarChangeListenersInitialized() {
	if (ratingBarChangeListeners == null) {
	    ratingBarChangeListeners = new OnRatingBarChangeListeners();
	    ratingBar.setOnRatingBarChangeListener(ratingBarChangeListeners);
	}
    }
}
