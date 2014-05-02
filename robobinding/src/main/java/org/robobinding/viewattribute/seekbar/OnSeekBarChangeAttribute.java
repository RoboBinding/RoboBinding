package org.robobinding.viewattribute.seekbar;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnSeekBarChangeAttribute extends AbstractCommandViewAttribute<SeekBar> implements ViewListenersAware<SeekBarListeners> {
    private SeekBarListeners viewListeners;

    @Override
    public void setViewListeners(SeekBarListeners viewListeners) {
	this.viewListeners = viewListeners;
    }

    @Override
    protected void bind(final Command command) {
	viewListeners.addOnSeekBarChangeListener(new OnSeekBarChangeListener() {

	    @Override
	    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		SeekBarEvent seekBarEvent = new SeekBarEvent(seekBar, progress, fromUser);
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
    protected Class<?> getPreferredCommandParameterType() {
	return SeekBarEvent.class;
    }
}
