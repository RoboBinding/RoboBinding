package org.robobinding.widget.seekbar;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayProgressAttribute implements TwoWayPropertyViewAttribute<SeekBar, Integer>, ViewListenersAware<SeekBarListeners> {
    private SeekBarListeners viewListeners;

    @Override
    public void setViewListeners(SeekBarListeners viewListeners) {
	this.viewListeners = viewListeners;
    }

    @Override
    public void updateView(SeekBar view, Integer progress) {
	view.setProgress(progress);
    }

    @Override
    public void observeChangesOnTheView(SeekBar view, final ValueModel<Integer> valueModel) {
	viewListeners.addOnSeekBarChangeListener(new OnSeekBarChangeListener() {

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
