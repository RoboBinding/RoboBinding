package org.robobinding.widgetaddon.seekbar;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
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
public class SeekBarAddOnTest {
	private SeekBar view;
	private SeekBarAddOn viewListeners;
	
	@Before
	public void setUp() {
		view = new SeekBar(RuntimeEnvironment.application);
		viewListeners = new SeekBarAddOn(view);
	}
	
	@Test
	public void shouldSupportMultipleOnSeekBarChangeListeners() {
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
