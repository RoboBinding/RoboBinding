package org.robobinding.widget.seekbar;

import org.robobinding.widgetaddon.seekbar.SeekBarAddOn;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockSeekBarAddOn extends SeekBarAddOn {
	private final SeekBar seekBar;
	public boolean addOnSeekBarChangeListenerInvoked;

	public MockSeekBarAddOn(SeekBar seekBar) {
		super(null);
		this.seekBar = seekBar;
	}

	@Override
	public void addOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
		addOnSeekBarChangeListenerInvoked = true;
		seekBar.setOnSeekBarChangeListener(listener);
	}
}
