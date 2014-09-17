package org.robobinding.widget.seekbar;

import org.robobinding.widget.view.AbstractViewEvent;

import android.widget.SeekBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class SeekBarChangeEvent extends AbstractViewEvent {
	private final int progress;
	private final boolean fromUser;

	public SeekBarChangeEvent(SeekBar seekBar, int progress, boolean fromUser) {
		super(seekBar);
		this.progress = progress;
		this.fromUser = fromUser;
	}

	public int getProgress() {
		return progress;
	}

	public boolean isFromUser() {
		return fromUser;
	}

	@Override
	public SeekBar getView() {
		return (SeekBar) super.getView();
	}

}
