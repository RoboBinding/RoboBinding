package org.robobinding.widget.seekbar;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class SeekBarListenersTest {
	@Test
	public void shouldSupportMultipleOnSeekBarChangeListeners() {
		SeekBar view = new SeekBar(Robolectric.application);
		SeekBarListeners viewListeners = new SeekBarListeners(view);

		MockOnSeekBarChangeListener listener1 = new MockOnSeekBarChangeListener();
		MockOnSeekBarChangeListener listener2 = new MockOnSeekBarChangeListener();

		viewListeners.addOnSeekBarChangeListener(listener1);
		viewListeners.addOnSeekBarChangeListener(listener2);

		int newProgressValue = RandomValues.anyInteger();
		view.setProgress(newProgressValue);

		assertTrue(listener1.seekBarChangeEventFired);
		assertTrue(listener2.seekBarChangeEventFired);
	}

	private static class MockOnSeekBarChangeListener implements OnSeekBarChangeListener {
		private boolean seekBarChangeEventFired;

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			seekBarChangeEventFired = true;
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	}
}
