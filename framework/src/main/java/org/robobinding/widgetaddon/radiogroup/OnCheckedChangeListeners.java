package org.robobinding.widgetaddon.radiogroup;

import org.robobinding.widgetaddon.AbstractListeners;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class OnCheckedChangeListeners extends AbstractListeners<OnCheckedChangeListener> implements OnCheckedChangeListener {
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		for (OnCheckedChangeListener listener : listeners) {
			listener.onCheckedChanged(group, checkedId);
		}
	}

}