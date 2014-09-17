package org.robobinding.widget.ratingbar;

import org.robobinding.widget.view.AbstractViewEvent;

import android.widget.RatingBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingBarChangeEvent extends AbstractViewEvent {
	private float rating;
	private boolean fromUser;

	public RatingBarChangeEvent(RatingBar ratingBar, float rating, boolean fromUser) {
		super(ratingBar);
		this.rating = rating;
		this.fromUser = fromUser;
	}

	@Override
	public RatingBar getView() {
		return (RatingBar) super.getView();
	}

	public float getRating() {
		return rating;
	}

	public boolean isFromUser() {
		return fromUser;
	}

}
