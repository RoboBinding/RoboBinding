package org.robobinding.widget.ratingbar;

import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.ViewBinding;

import android.widget.RatingBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingBarBinding implements ViewBinding<RatingBar> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<RatingBar> mappings) {
		mappings.mapMultiTypeProperty(RatingAttribute.class, "rating");
		mappings.mapProperty(NumStarsAttribute.class, "numStars");

		mappings.mapEvent(OnRatingBarChangeAttribute.class, "onRatingBarChange");
	}
}
