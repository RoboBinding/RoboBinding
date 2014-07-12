package org.robobinding.widget.ratingbar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.robobinding.viewattribute.RandomValues.nextInt;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.event.AbstractEventViewAttributeWithViewListenersAwareTest;

import android.widget.RatingBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnRatingBarChangeAttributeTest extends
	AbstractEventViewAttributeWithViewListenersAwareTest<RatingBar, OnRatingBarChangeAttribute, MockRatingBarListeners> {
    private static final int NUM_STARS_TO_SHOW = 5;

    @Before
    public void setUp() {
	view.setNumStars(NUM_STARS_TO_SHOW);
    }

    @Test
    public void givenBoundAttribute_whenChangeChecked_thenEventReceived() {
	bindAttribute();

	int newNumStars = nextInt(NUM_STARS_TO_SHOW);
	updateRating(newNumStars);

	assertEventReceived(newNumStars);
    }

    private void updateRating(int newNumStars) {
        view.setRating(newNumStars);
    }

    private void assertEventReceived(int newNumStars) {
        assertEventReceived(RatingBarEvent.class);
        RatingBarEvent ratingBarEvent = getEventReceived();
        assertThat(ratingBarEvent.getRatingBar(), sameInstance(view));
        assertThat((double) ratingBarEvent.getRating(), is(closeTo(newNumStars, 0.1f)));
        assertTrue(ratingBarEvent.isFromUser());
    }

    @Test
    public void whenBinding_thenRegisterWithMulticastListener() {
	bindAttribute();

	assertTrue(viewListeners.addOnRatingBarChangeListenerInvoked);
    }
}
