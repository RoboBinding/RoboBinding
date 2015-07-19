package org.robobinding.widgetaddon.seekbar;

import org.robobinding.widgetaddon.AbstractListeners;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class OnSeekBarChangeListeners extends AbstractListeners<OnSeekBarChangeListener> implements OnSeekBarChangeListener {
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		for (OnSeekBarChangeListener onSeekBarChangeListener : listeners) {
			onSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		for (OnSeekBarChangeListener onSeekBarChangeListener : listeners) {
			onSeekBarChangeListener.onStartTrackingTouch(seekBar);
		}
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		for (OnSeekBarChangeListener onSeekBarChangeListener : listeners) {
			onSeekBarChangeListener.onStopTrackingTouch(seekBar);
		}
	}
}
