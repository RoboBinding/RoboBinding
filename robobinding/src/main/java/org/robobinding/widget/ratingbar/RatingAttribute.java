package org.robobinding.widget.ratingbar;

import org.robobinding.property.ValueModel;
import org.robobinding.util.PrimitiveTypeUtils;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widget.view.ViewListenersAware;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingAttribute implements MultiTypePropertyViewAttribute<RatingBar> {
    @Override
    public TwoWayPropertyViewAttribute<RatingBar, ?> create(RatingBar view, Class<?> propertyType) {
	if (PrimitiveTypeUtils.floatIsAssignableFrom(propertyType)) {
	    return new FloatRatingAttribute();
	} else if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType)) {
	    return new IntegerRatingAttribute();
	} else
	    throw new RuntimeException("Could not find a suitable rating attribute class for property type: " + propertyType);
    }

    abstract static class AbstractRatingAttribute<T> implements TwoWayPropertyViewAttribute<RatingBar, T>, ViewListenersAware<RatingBarListeners> {
	protected RatingBarListeners ratingBarListeners;

	@Override
	public void setViewListeners(RatingBarListeners ratingBarListeners) {
	    this.ratingBarListeners = ratingBarListeners;
	}

    }

    static class FloatRatingAttribute extends AbstractRatingAttribute<Float> {
	@Override
	public void updateView(RatingBar view, Float newRating) {
	    view.setRating(newRating);
	}

	@Override
	public void observeChangesOnTheView(RatingBar view, final ValueModel<Float> valueModel) {
	    ratingBarListeners.addOnRatingBarChangeListener(new OnRatingBarChangeListener() {

		@Override
		public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		    valueModel.setValue(rating);
		}
	    });
	}
    }

    static class IntegerRatingAttribute extends AbstractRatingAttribute<Integer> {
	@Override
	public void updateView(RatingBar view, Integer newRating) {
	    view.setRating((float) newRating);
	}

	@Override
	public void observeChangesOnTheView(final RatingBar view, final ValueModel<Integer> valueModel) {
	    ratingBarListeners.addOnRatingBarChangeListener(new OnRatingBarChangeListener() {

		@Override
		public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		    valueModel.setValue((int) rating);
		}
	    });
	}
    }
}