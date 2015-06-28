package org.robobinding.widget.ratingbar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.EventCommand;
import org.robolectric.annotation.Config;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class OnRatingBarChangeAttributeTest extends AbstractRatingBarAttributeTest {
	private static final int NUM_STARS_TO_SHOW = 5;
	private OnRatingBarChangeAttribute attribute;
	private EventCommand eventCommand;

	@Before
	public void setUp() {
		view.setNumStars(NUM_STARS_TO_SHOW);
		attribute = new OnRatingBarChangeAttribute();
		eventCommand = new EventCommand();
	}

	@Test
	public void givenBoundAttribute_whenChangeChecked_thenEventReceived() {
		bindAttribute();

		int newNumStars = RandomValues.nextIntegerGreaterThanZero(NUM_STARS_TO_SHOW);
		updateRating(newNumStars);

		assertEventReceived(newNumStars);
	}

	private void bindAttribute() {
		attribute.bind(viewAddOn, eventCommand, view);
	}

	private void updateRating(int newNumStars) {
		view.setRating(newNumStars);
	}

	private void assertEventReceived(int newNumStars) {
		eventCommand.assertEventReceived(RatingBarChangeEvent.class);
		RatingBarChangeEvent ratingBarEvent = eventCommand.getEventReceived();
		assertThat(ratingBarEvent.getView(), sameInstance(view));
		assertThat((double) ratingBarEvent.getRating(), is(closeTo(newNumStars, 0.1f)));
		// assertTrue(ratingBarEvent.isFromUser());
	}

	@Test
	public void whenBinding_thenRegisterWithMulticastListener() {
		bindAttribute();

		assertTrue(viewAddOn.addOnRatingBarChangeListenerInvoked);
	}
}
