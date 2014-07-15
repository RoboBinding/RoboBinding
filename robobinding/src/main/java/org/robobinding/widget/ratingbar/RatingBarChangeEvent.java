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

    RatingBarChangeEvent(RatingBar ratingBar, float rating, boolean fromUser) {
	super(ratingBar);
	this.rating = rating;
	this.fromUser = fromUser;
    }

    public RatingBar getRatingBar() {
	return (RatingBar) getView();
    }

    public float getRating() {
	return rating;
    }

    public boolean isFromUser() {
	return fromUser;
    }

}
