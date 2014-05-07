package org.robobinding.viewattribute.ratingbar;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnRatingBarChangeAttribute extends AbstractCommandViewAttribute<RatingBar> implements ViewListenersAware<RatingBarListeners> {
    private RatingBarListeners ratingBarListeners;

    @Override
    protected void bind(final Command command) {
	ratingBarListeners.addOnRatingBarChangeListener(new OnRatingBarChangeListener() {
	    @Override
	    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		RatingBarEvent ratingBarEvent = new RatingBarEvent(ratingBar, rating, fromUser);
		command.invoke(ratingBarEvent);
	    }
	});
    }

    @Override
    protected Class<?> getPreferredCommandParameterType() {
	return RatingBarEvent.class;
    }

    @Override
    public void setViewListeners(RatingBarListeners ratingBarListeners) {
	this.ratingBarListeners = ratingBarListeners;
    }
}
