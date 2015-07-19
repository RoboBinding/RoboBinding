package org.robobinding.widget.ratingbar;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.util.RandomValues;
import org.robobinding.viewattribute.ValueModelUtils;
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
	private FloatRatingAttribute attribute;
	private float newRating;

	@Before
	public void prepareRatingBar() {
		attribute = new FloatRatingAttribute();

		view.setNumStars(NUM_STARS_TO_SHOW);
		newRating = RandomValues.nextIntegerGreaterThanZero(NUM_STARS_TO_SHOW);
	}

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		attribute.updateView(view, newRating, null);

		assertThat((double) view.getRating(), closeTo(newRating, 0.1));
	}

	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
		ValueModel<Float> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(viewAddOn, valueModel, view);

		view.setRating(newRating);

		assertThat((double) valueModel.getValue(), closeTo(view.getRating(), 0.1));
	}

	@Test
	public void whenObserveChangesOnTheView_thenRegisterWithMulticastListener() {
		FloatRatingAttribute attribute = new FloatRatingAttribute();
		attribute.observeChangesOnTheView(viewAddOn, null, view);

		assertTrue(viewAddOn.addOnRatingBarChangeListenerInvoked);
	}
}
