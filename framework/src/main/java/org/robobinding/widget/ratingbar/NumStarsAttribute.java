package org.robobinding.widget.ratingbar;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.widget.RatingBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class NumStarsAttribute implements OneWayPropertyViewAttribute<RatingBar, Integer> {
	@Override
	public void updateView(RatingBar view, Integer numStars) {
		view.setNumStars(numStars);
	}
}
