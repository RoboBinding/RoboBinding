package org.robobinding.widget.seekbar;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.event.EventViewAttribute;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnSeekBarChangeAttribute implements EventViewAttribute<SeekBar>, ViewListenersAware<SeekBarListeners> {
	private SeekBarListeners viewListeners;

	@Override
	public void setViewListeners(SeekBarListeners viewListeners) {
		this.viewListeners = viewListeners;
	}

	@Override
	public void bind(final SeekBar view, final Command command) {
		viewListeners.addOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				SeekBarChangeEvent seekBarEvent = new SeekBarChangeEvent(seekBar, progress, fromUser);
				command.invoke(seekBarEvent);
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

		});
	}

	@Override
	public Class<SeekBarChangeEvent> getEventType() {
		return SeekBarChangeEvent.class;
	}
}
