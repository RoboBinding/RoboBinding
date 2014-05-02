package org.robobinding.viewattribute.ratingbar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.robobinding.viewattribute.RandomValues.nextInt;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.view.AbstractCommandViewAttributeWithViewListenersAwareTest;

import android.widget.RatingBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnRatingBarChangeAttributeTest extends
	AbstractCommandViewAttributeWithViewListenersAwareTest<RatingBar, OnRatingBarChangeAttribute, MockRatingBarListeners> {
    private static final int NUM_STARS_TO_SHOW = 5;
    private int newNumStars;

    @Before
    public void setUp() {
	view.setNumStars(NUM_STARS_TO_SHOW);
	newNumStars = nextInt(NUM_STARS_TO_SHOW);
    }

    @Test
    public void givenBoundAttribute_whenChangeChecked_thenEventReceived() {
	bindAttribute();

	updateRating();

	assertEventReceived();
    }

    @Test
    public void whenBinding_thenRegisterWithMulticastListener() {
	bindAttribute();

	assertTrue(viewListeners.addOnRatingBarChangeListenerInvoked);
    }

    private void updateRating() {
	view.setRating(newNumStars);
    }

    private void assertEventReceived() {
	assertEventReceived(RatingBarEvent.class);
	RatingBarEvent ratingBarEvent = getEventReceived();
	assertThat(ratingBarEvent.getRatingBar(), sameInstance(view));
	assertThat((double) ratingBarEvent.getRating(), is(closeTo(newNumStars, 0.1f)));
	assertTrue(ratingBarEvent.isFromUser());
    }
}
