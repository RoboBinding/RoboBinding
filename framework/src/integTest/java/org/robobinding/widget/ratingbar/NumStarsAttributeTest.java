package org.robobinding.widget.ratingbar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
import org.robolectric.annotation.Config;

import android.widget.RatingBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class NumStarsAttributeTest extends
		AbstractPropertyViewAttributeTest<RatingBar, NumStarsAttribute> {
	@Test
	public void whenUpdateView_thenSetNumStarsOnRatingBar() {
		int newNumStars = 10;

		attribute.updateView(view, newNumStars);

		assertThat(view.getNumStars(), is(newNumStars));
	}
}
