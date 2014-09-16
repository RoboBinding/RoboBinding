package org.robobinding.widget.ratingbar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.widget.RatingBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class NumStarsAttributeTest {
	@Test
	public void whenUpdateView_thenSetNumStarsOnRatingBar() {
		RatingBar view = new RatingBar(Robolectric.application);
		NumStarsAttribute attribute = new NumStarsAttribute();
		int newNumStars = RandomValues.nextInt(10);

		attribute.updateView(view, newNumStars);

		assertThat(view.getNumStars(), is(newNumStars));
	}
}
