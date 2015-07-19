package org.robobinding.widget.seekbar;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
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
public class TwoWayProgressAttribute implements TwoWayPropertyViewAttribute<SeekBar, SeekBarAddOn, Integer> {
	@Override
	public void updateView(SeekBar view, Integer progress, SeekBarAddOn viewAddOn) {
		view.setProgress(progress);
	}

	@Override
	public void observeChangesOnTheView(SeekBarAddOn viewAddOn, final ValueModel<Integer> valueModel, SeekBar view) {
		viewAddOn.addOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				valueModel.setValue(progress);
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
		});
	}
}
