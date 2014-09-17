package org.robobinding.widget.compoundbutton;

import org.robobinding.widget.view.ViewListenersForView;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CompoundButtonListeners extends ViewListenersForView {
	CompoundButton compoundButton;
	private OnCheckedChangeListeners onCheckedChangeListeners;

	public CompoundButtonListeners(CompoundButton compoundButton) {
		super(compoundButton);
		this.compoundButton = compoundButton;
	}

	public void addOnCheckedChangeListener(OnCheckedChangeListener listener) {
		ensureOnCheckedChangeListenersInitialized();
		onCheckedChangeListeners.addListener(listener);
	}

	private void ensureOnCheckedChangeListenersInitialized() {
		if (onCheckedChangeListeners == null) {
			onCheckedChangeListeners = new OnCheckedChangeListeners();
			compoundButton.setOnCheckedChangeListener(onCheckedChangeListeners);
		}
	}
}
