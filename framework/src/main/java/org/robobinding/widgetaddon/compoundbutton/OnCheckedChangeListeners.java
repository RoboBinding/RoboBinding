package org.robobinding.widgetaddon.compoundbutton;

import org.robobinding.widgetaddon.AbstractListeners;

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
