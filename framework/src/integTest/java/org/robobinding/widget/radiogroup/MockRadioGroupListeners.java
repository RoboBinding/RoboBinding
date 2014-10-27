package org.robobinding.widget.radiogroup;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockRadioGroupListeners extends RadioGroupListeners {
	public boolean addOnCheckedChangeListenerInvoked;

	public MockRadioGroupListeners(RadioGroup view) {
		super(view);
	}

	@Override
	public void addOnCheckedChangeListener(OnCheckedChangeListener listener) {
		addOnCheckedChangeListenerInvoked = true;
		super.addOnCheckedChangeListener(listener);
	}
}
