package org.robobinding.widget.ratingbar;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

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
		mappings.mapTwoWayMultiTypeProperty(RatingAttribute.class, "rating");

		mappings.mapEvent(OnRatingBarChangeAttribute.class, "onRatingBarChange");
	}
}
