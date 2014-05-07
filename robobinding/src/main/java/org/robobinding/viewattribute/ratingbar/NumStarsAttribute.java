package org.robobinding.viewattribute.ratingbar;

import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;

import android.widget.RatingBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class NumStarsAttribute extends AbstractReadOnlyPropertyViewAttribute<RatingBar, Integer> {
    @Override
    protected void valueModelUpdated(Integer numStars) {
	view.setNumStars(numStars);
    }
}
