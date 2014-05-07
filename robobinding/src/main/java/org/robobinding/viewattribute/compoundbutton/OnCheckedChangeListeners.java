package org.robobinding.viewattribute.compoundbutton;

import org.robobinding.viewattribute.AbstractListeners;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class OnCheckedChangeListeners extends AbstractListeners<OnCheckedChangeListener> implements OnCheckedChangeListener {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	for (OnCheckedChangeListener listener : listeners) {
	    listener.onCheckedChanged(buttonView, isChecked);
	}
    }

}
