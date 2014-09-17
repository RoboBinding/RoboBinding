package org.robobinding.widget.ratingbar;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.ratingbar.RatingAttribute.FloatRatingAttribute;
import org.robolectric.annotation.Config;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class FloatRatingAttributeTest extends AbstractRatingBarAttributeTest {
	private static final int NUM_STARS_TO_SHOW = 5;
	private float newRating;

	@Before
	public void prepareRatingBar() {
		view.setNumStars(NUM_STARS_TO_SHOW);
		newRating = RandomValues.nextIntegerGreaterThanZero(NUM_STARS_TO_SHOW);
	}

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		FloatRatingAttribute attribute = new FloatRatingAttribute();

		attribute.updateView(view, newRating);

		assertThat((double) view.getRating(), closeTo(newRating, 0.1));
	}

	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
		FloatRatingAttribute attribute = withListenersSet(new FloatRatingAttribute());
		ValueModel<Float> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(view, valueModel);

		view.setRating(newRating);

		assertThat((double) valueModel.getValue(), closeTo(view.getRating(), 0.1));
	}

	@Test
	public void whenObserveChangesOnTheView_thenRegisterWithMulticastListener() {
		FloatRatingAttribute attribute = withListenersSet(new FloatRatingAttribute());
		attribute.observeChangesOnTheView(view, null);

		assertTrue(viewListeners.addOnRatingBarChangeListenerInvoked);
	}
}
