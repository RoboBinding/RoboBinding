package org.robobinding.widget.radiogroup;

import org.robobinding.viewattribute.AbstractListeners;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnCheckedChangeListeners extends AbstractListeners<OnCheckedChangeListener> implements OnCheckedChangeListener {
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		for (OnCheckedChangeListener listener : listeners) {
			listener.onCheckedChanged(group, checkedId);
		}
	}

}