package org.robobinding.widget.ratingbar;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.robobinding.viewattribute.RandomValues.anyFloat;
import static org.robobinding.viewattribute.RandomValues.nextFloat;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.viewattribute.property.AbstractPropertyViewAttributeWithViewListenersAwareTest;
import org.robobinding.widget.ratingbar.RatingAttribute.FloatRatingAttribute;

import android.widget.RatingBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class FloatRatingAttributeTest extends
	AbstractPropertyViewAttributeWithViewListenersAwareTest<RatingBar, FloatRatingAttribute, MockRatingBarListeners> {
    private static final int NUM_STARS_TO_SHOW = 5;

    @Before
    public void prepareRatingBar() {
	view.setNumStars(NUM_STARS_TO_SHOW);
    }

    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
	float newRating = nextFloat(NUM_STARS_TO_SHOW);

	attribute.updateView(view, newRating);

	assertThat((double) view.getRating(), closeTo(newRating, 0.1));
    }

    @Test
    public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
	ValueModel<Float> valueModel = ValueModelUtils.create();
	attribute.observeChangesOnTheView(view, valueModel);

	view.setRating(anyFloat());

	assertThat((double) valueModel.getValue(), closeTo(view.getRating(), 0.1));
    }

    @Test
    public void whenObserveChangesOnTheView_thenRegisterWithMulticastListener() {
	attribute.observeChangesOnTheView(view, null);

	assertTrue(viewListeners.addOnRatingBarChangeListenerInvoked);
    }
}
