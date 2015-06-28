package org.robobinding.widget.radiogroup;

import org.robobinding.widgetaddon.radiogroup.RadioGroupAddOn;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockRadioGroupAddOn extends RadioGroupAddOn {
	private RadioGroup radioGroup;
	public boolean addOnCheckedChangeListenerInvoked;

	public MockRadioGroupAddOn(RadioGroup radioGroup) {
		super(null);
		this.radioGroup = radioGroup;
	}

	@Override
	public void addOnCheckedChangeListener(OnCheckedChangeListener listener) {
		addOnCheckedChangeListenerInvoked = true;
		radioGroup.setOnCheckedChangeListener(listener);
	}
}
