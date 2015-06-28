package org.robobinding.widget.compoundbutton;

import org.robobinding.widgetaddon.compoundbutton.CompoundButtonAddOn;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockCompoundButtonAddOn extends CompoundButtonAddOn {
	private final CompoundButton compoundButton;
	public boolean addOnCheckedChangeListenerInvoked;

	public MockCompoundButtonAddOn(CompoundButton compoundButton) {
		super(compoundButton);
		this.compoundButton = compoundButton;
	}

	@Override
	public void addOnCheckedChangeListener(OnCheckedChangeListener listener) {
		addOnCheckedChangeListenerInvoked = true;
		compoundButton.setOnCheckedChangeListener(listener);
	}
}
