package org.robobinding.viewattribute.ratingbar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;

import android.widget.RatingBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class NumStarsAttributeTest extends AbstractPropertyViewAttributeTest<RatingBar, NumStarsAttribute> {
    @Test
    public void whenUpdatingValueModel_thenSetNumStarsOnRatingBar() {
	int newNumStars = 10;

	attribute.valueModelUpdated(newNumStars);

	assertThat(view.getNumStars(), is(newNumStars));
    }
}
