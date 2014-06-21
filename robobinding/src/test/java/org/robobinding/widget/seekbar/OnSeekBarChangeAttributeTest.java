package org.robobinding.widget.seekbar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.event.AbstractEventViewAttributeWithViewListenersAwareTest;

import android.widget.SeekBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnSeekBarChangeAttributeTest extends
	AbstractEventViewAttributeWithViewListenersAwareTest<SeekBar, OnSeekBarChangeAttribute, MockSeekBarListeners> {
    @Test
    public void givenBoundAttribute_whenUpdatingProgress_thenEventReceived() {
	bindAttribute();

	int newProgressValue = RandomValues.anyInteger();
	updateProgressOnSeekBar(newProgressValue);

	assertEventReceived(newProgressValue);
    }

    private void updateProgressOnSeekBar(int newProgressValue) {
        view.setProgress(newProgressValue);
    }

    private void assertEventReceived(int newProgressValue) {
        assertEventReceived(SeekBarEvent.class);
        SeekBarEvent seekBarEvent = getEventReceived();
        assertThat(seekBarEvent.getSeekBar(), sameInstance(view));
        assertThat(seekBarEvent.getProgress(), is(newProgressValue));
        assertTrue(seekBarEvent.isFromUser());
    }

    @Test
    public void whenBinding_thenRegisterWithViewListeners() {
	bindAttribute();

	assertTrue(viewListeners.addOnSeekBarChangeListenerInvoked);
    }
}
