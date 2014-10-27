package org.robobinding.widget.seekbar;

import org.robobinding.widget.view.ViewListenersForView;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SeekBarListeners extends ViewListenersForView {
	SeekBar seekBar;
	private OnSeekBarChangeListeners onSeekBarChangeListeners;

	public SeekBarListeners(SeekBar seekBar) {
		super(seekBar);
		this.seekBar = seekBar;
	}

	public void addOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
		ensureOnSeekBarChangeListenersInitialized();
		onSeekBarChangeListeners.addListener(listener);
	}

	private void ensureOnSeekBarChangeListenersInitialized() {
		if (onSeekBarChangeListeners == null) {
			onSeekBarChangeListeners = new OnSeekBarChangeListeners();
			seekBar.setOnSeekBarChangeListener(onSeekBarChangeListeners);
		}
	}
}
