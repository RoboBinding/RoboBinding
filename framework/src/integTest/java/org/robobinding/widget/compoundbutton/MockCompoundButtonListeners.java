package org.robobinding.widget.compoundbutton;

import org.robobinding.widgetaddon.compoundbutton.CompoundButtonListeners;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockCompoundButtonListeners extends CompoundButtonListeners {
	public boolean addOnCheckedChangeListenerInvoked;

	public MockCompoundButtonListeners(CompoundButton compoundButton) {
		super(compoundButton);
	}

	@Override
	public void addOnCheckedChangeListener(OnCheckedChangeListener listener) {
		addOnCheckedChangeListenerInvoked = true;
		compoundButton.setOnCheckedChangeListener(listener);
	}
}
