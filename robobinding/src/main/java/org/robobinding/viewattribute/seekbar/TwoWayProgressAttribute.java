package org.robobinding.viewattribute.seekbar;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayProgressAttribute extends AbstractPropertyViewAttribute<SeekBar, Integer> implements ViewListenersAware<SeekBarListeners> {
    private SeekBarListeners viewListeners;

    @Override
    public void setViewListeners(SeekBarListeners viewListeners) {
	this.viewListeners = viewListeners;
    }

    @Override
    protected void valueModelUpdated(Integer progress) {
	view.setProgress(progress);
    }

    @Override
    protected void observeChangesOnTheView(final ValueModel<Integer> valueModel) {
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
