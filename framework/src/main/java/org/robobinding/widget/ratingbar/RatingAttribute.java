package org.robobinding.widget.ratingbar;

import org.robobinding.property.ValueModel;
import org.robobinding.util.PrimitiveTypeUtils;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widgetaddon.ratingbar.RatingBarAddOn;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingAttribute implements TwoWayMultiTypePropertyViewAttribute<RatingBar> {
	@Override
	public TwoWayPropertyViewAttribute<RatingBar, ?, ?> create(RatingBar view, Class<?> propertyType) {
		if (PrimitiveTypeUtils.floatIsAssignableFrom(propertyType)) {
			return new FloatRatingAttribute();
		} else if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType)) {
			return new IntegerRatingAttribute();
		} else
			throw new RuntimeException("Could not find a suitable rating attribute class for property type: " + propertyType);
	}

	static class FloatRatingAttribute implements TwoWayPropertyViewAttribute<RatingBar, RatingBarAddOn, Float> {
		@Override
		public void updateView(RatingBar view, Float newRating, RatingBarAddOn viewAddOn) {
			view.setRating(newRating);
		}

		@Override
		public void observeChangesOnTheView(RatingBarAddOn viewAddOn, final ValueModel<Float> valueModel, RatingBar view) {
			viewAddOn.addOnRatingBarChangeListener(new OnRatingBarChangeListener() {

				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
					valueModel.setValue(rating);
				}
			});
		}
	}

	static class IntegerRatingAttribute implements TwoWayPropertyViewAttribute<RatingBar, RatingBarAddOn, Integer> {
		@Override
		public void updateView(RatingBar view, Integer newRating, RatingBarAddOn viewAddOn) {
			view.setRating((float) newRating);
		}

		@Override
		public void observeChangesOnTheView(RatingBarAddOn viewAddOn, final ValueModel<Integer> valueModel, RatingBar view) {
			viewAddOn.addOnRatingBarChangeListener(new OnRatingBarChangeListener() {

				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
					valueModel.setValue((int) rating);
				}
			});
		}
	}
}