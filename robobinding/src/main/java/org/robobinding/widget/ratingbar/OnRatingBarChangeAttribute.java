package org.robobinding.widget.ratingbar;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.widget.view.ViewListenersAware;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnRatingBarChangeAttribute implements EventViewAttribute<RatingBar>, ViewListenersAware<RatingBarListeners> {
    private RatingBarListeners ratingBarListeners;

    @Override
    public void bind(final RatingBar view, final Command command) {
	ratingBarListeners.addOnRatingBarChangeListener(new OnRatingBarChangeListener() {
	    @Override
	    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		RatingBarEvent ratingBarEvent = new RatingBarEvent(ratingBar, rating, fromUser);
		command.invoke(ratingBarEvent);
	    }
	});
    }

    @Override
    public Class<RatingBarEvent> getEventType() {
	return RatingBarEvent.class;
    }

    @Override
    public void setViewListeners(RatingBarListeners ratingBarListeners) {
	this.ratingBarListeners = ratingBarListeners;
    }
}
