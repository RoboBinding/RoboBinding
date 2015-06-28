package org.robobinding.widget.ratingbar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.util.RandomValues;
import org.robobinding.viewattribute.ValueModelUtils;
import org.robobinding.widget.ratingbar.RatingAttribute.IntegerRatingAttribute;
import org.robolectric.annotation.Config;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class IntegerRatingAttributeTest extends AbstractRatingBarAttributeTest {
	private static final int NUM_STARS_TO_SHOW = 5;
	private IntegerRatingAttribute attribute;
	private int newRating;

	@Before
	public void prepareRatingBar() {
		attribute = new IntegerRatingAttribute();

		view.setNumStars(NUM_STARS_TO_SHOW);
		newRating = RandomValues.nextIntegerGreaterThanZero(NUM_STARS_TO_SHOW);
	}

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		attribute.updateView(view, newRating, null);

		assertThat(view.getRating(), equalTo((float) newRating));
	}

	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
		IntegerRatingAttribute attribute = new IntegerRatingAttribute();
		ValueModel<Integer> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(viewAddOn, valueModel, view);

		view.setRating(newRating);

		assertThat((float) valueModel.getValue(), equalTo(view.getRating()));
	}

	@Test
	public void whenObserveChangesOnTheView_thenRegisterWithMulticastListener() {
		IntegerRatingAttribute attribute = new IntegerRatingAttribute();
		attribute.observeChangesOnTheView(viewAddOn, null, view);

		assertTrue(viewAddOn.addOnRatingBarChangeListenerInvoked);
	}
}
