package org.robobinding.widget.seekbar;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.widgetaddon.seekbar.SeekBarAddOn;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class OnSeekBarChangeAttribute implements EventViewAttribute<SeekBar, SeekBarAddOn> {
	@Override
	public void bind(SeekBarAddOn viewAddOn, final Command command, SeekBar view) {
		viewAddOn.addOnSeekBarChangeListener(new OnSeekBarChangeListener() {

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
