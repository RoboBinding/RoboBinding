package org.robobinding.widget.seekbar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
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
public class OnSeekBarChangeAttributeTest extends AbstractSeekBarAttributeTest {
	private OnSeekBarChangeAttribute attribute;
	private EventCommand eventCommand;

	@Before
	public void setUp() {
		attribute = new OnSeekBarChangeAttribute();
		eventCommand = new EventCommand();
	}

	@Test
	public void givenBoundAttribute_whenUpdatingProgress_thenEventReceived() {
		bindAttribute();

		int newProgressValue = RandomValues.anyInteger();
		updateProgressOnSeekBar(newProgressValue);

		assertEventReceived(newProgressValue);
	}

	private void bindAttribute() {
		attribute.bind(viewAddOn, eventCommand, view);
	}

	private void updateProgressOnSeekBar(int newProgressValue) {
		view.setProgress(newProgressValue);
	}

	private void assertEventReceived(int newProgressValue) {
		eventCommand.assertEventReceived(SeekBarChangeEvent.class);
		SeekBarChangeEvent seekBarEvent = eventCommand.getEventReceived();
		assertThat(seekBarEvent.getView(), sameInstance(view));
		assertThat(seekBarEvent.getProgress(), is(newProgressValue));
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		bindAttribute();

		assertTrue(viewAddOn.addOnSeekBarChangeListenerInvoked);
	}
}
