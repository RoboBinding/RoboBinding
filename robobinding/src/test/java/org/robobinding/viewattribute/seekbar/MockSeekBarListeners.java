package org.robobinding.viewattribute.seekbar;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockSeekBarListeners extends SeekBarListeners {
    public boolean addOnSeekBarChangeListenerInvoked;

    public MockSeekBarListeners(SeekBar seekBar) {
	super(seekBar);
    }

    @Override
    public void addOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
	addOnSeekBarChangeListenerInvoked = true;
	seekBar.setOnSeekBarChangeListener(listener);
    }
}
