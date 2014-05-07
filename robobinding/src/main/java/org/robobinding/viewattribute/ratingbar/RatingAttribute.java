package org.robobinding.viewattribute.ratingbar;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.PrimitiveTypeUtils;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingAttribute extends AbstractMultiTypePropertyViewAttribute<RatingBar> {
    @Override
    protected AbstractPropertyViewAttribute<RatingBar, ?> createPropertyViewAttribute(Class<?> propertyType) {
	if (PrimitiveTypeUtils.floatIsAssignableFrom(propertyType)) {
	    return new FloatRatingAttribute();
	} else if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType)) {
	    return new IntegerRatingAttribute();
	} else
	    throw new RuntimeException("Could not find a suitable rating attribute class for property type: " + propertyType);
    }

    abstract static class AbstractRatingAttribute<T> extends AbstractPropertyViewAttribute<RatingBar, T> implements
	    ViewListenersAware<RatingBarListeners> {
	protected RatingBarListeners ratingBarListeners;

	@Override
	public void setViewListeners(RatingBarListeners ratingBarListeners) {
	    this.ratingBarListeners = ratingBarListeners;
	}

    }

    static class FloatRatingAttribute extends AbstractRatingAttribute<Float> {
	@Override
	protected void valueModelUpdated(Float newRating) {
	    view.setRating(newRating);
	}

	@Override
	protected void observeChangesOnTheView(final ValueModel<Float> valueModel) {
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
	protected void valueModelUpdated(Integer newRating) {
	    view.setRating((float) newRating);
	}

	@Override
	protected void observeChangesOnTheView(final ValueModel<Integer> valueModel) {
	    ratingBarListeners.addOnRatingBarChangeListener(new OnRatingBarChangeListener() {

		@Override
		public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		    valueModel.setValue((int) rating);
		}
	    });
	}
    }
}