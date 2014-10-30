package org.robobinding.widgetaddon.compoundbutton;

import org.robobinding.widgetaddon.view.ViewAddOnForView;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class CompoundButtonAddOn extends ViewAddOnForView {
	private final CompoundButton view;
	private OnCheckedChangeListeners onCheckedChangeListeners;

	public CompoundButtonAddOn(CompoundButton view) {
		super(view);
		
		this.view = view;
	}

	public void addOnCheckedChangeListener(OnCheckedChangeListener listener) {
		ensureOnCheckedChangeListenersInitialized();
		onCheckedChangeListeners.addListener(listener);
	}

	private void ensureOnCheckedChangeListenersInitialized() {
		if (onCheckedChangeListeners == null) {
			onCheckedChangeListeners = new OnCheckedChangeListeners();
			view.setOnCheckedChangeListener(onCheckedChangeListeners);
		}
	}
}
