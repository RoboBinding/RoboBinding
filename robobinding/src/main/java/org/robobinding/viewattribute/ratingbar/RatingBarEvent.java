package org.robobinding.viewattribute.ratingbar;

import org.robobinding.viewattribute.view.AbstractViewEvent;

import android.widget.RatingBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingBarEvent extends AbstractViewEvent {
    private float rating;
    private boolean fromUser;

    RatingBarEvent(RatingBar ratingBar, float rating, boolean fromUser) {
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
