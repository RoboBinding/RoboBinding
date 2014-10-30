package org.robobinding.widgetaddon.seekbar;

import org.robobinding.widgetaddon.view.ViewAddOnForView;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SeekBarAddOn extends ViewAddOnForView {
	private final SeekBar view;
	private OnSeekBarChangeListeners onSeekBarChangeListeners;

	public SeekBarAddOn(SeekBar view) {
		super(view);
		this.view = view;
	}

	public void addOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
		ensureOnSeekBarChangeListenersInitialized();
		onSeekBarChangeListeners.addListener(listener);
	}

	private void ensureOnSeekBarChangeListenersInitialized() {
		if (onSeekBarChangeListeners == null) {
			onSeekBarChangeListeners = new OnSeekBarChangeListeners();
			view.setOnSeekBarChangeListener(onSeekBarChangeListeners);
		}
	}
}
