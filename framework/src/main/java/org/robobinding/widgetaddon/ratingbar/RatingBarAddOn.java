package org.robobinding.widgetaddon.ratingbar;

import org.robobinding.widgetaddon.view.ViewAddOnForView;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class RatingBarAddOn extends ViewAddOnForView {
	private final RatingBar view;
	private OnRatingBarChangeListeners onRatingBarChangeListeners;

	public RatingBarAddOn(RatingBar view) {
		super(view);
		
		this.view = view;
	}

	public void addOnRatingBarChangeListener(OnRatingBarChangeListener listener) {
		ensureOnRatingBarChangeListenersInitialized();
		onRatingBarChangeListeners.addListener(listener);
	}

	private void ensureOnRatingBarChangeListenersInitialized() {
		if (onRatingBarChangeListeners == null) {
			onRatingBarChangeListeners = new OnRatingBarChangeListeners();
			view.setOnRatingBarChangeListener(onRatingBarChangeListeners);
		}
	}

}
