package org.robobinding.widget.ratingbar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.robobinding.util.RandomValues.anyInteger;
import static org.robobinding.util.RandomValues.nextInt;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.widget.AbstractPropertyViewAttributeWithViewListenersAwareTest;
import org.robobinding.widget.ratingbar.RatingAttribute.IntegerRatingAttribute;
import org.robolectric.annotation.Config;

import android.widget.RatingBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class IntegerRatingAttributeTest extends
		AbstractPropertyViewAttributeWithViewListenersAwareTest<RatingBar, IntegerRatingAttribute, MockRatingBarListeners> {
	private static final int NUM_STARS_TO_SHOW = 5;

	@Before
	public void prepareRatingBar() {
		view.setNumStars(NUM_STARS_TO_SHOW);
	}

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		int newRating = nextInt(NUM_STARS_TO_SHOW);

		attribute.updateView(view, newRating);

		assertThat(view.getRating(), equalTo((float) newRating));
	}

	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
		ValueModel<Integer> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(view, valueModel);

		view.setRating(anyInteger());

		assertThat((float) valueModel.getValue(), equalTo(view.getRating()));
	}

	@Test
	public void whenObserveChangesOnTheView_thenRegisterWithMulticastListener() {
		attribute.observeChangesOnTheView(view, null);

		assertTrue(viewListeners.addOnRatingBarChangeListenerInvoked);
	}
}
