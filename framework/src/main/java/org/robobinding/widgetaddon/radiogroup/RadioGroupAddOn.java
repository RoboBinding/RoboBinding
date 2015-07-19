package org.robobinding.widgetaddon.radiogroup;

import org.robobinding.widgetaddon.view.ViewAddOnForView;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class RadioGroupAddOn extends ViewAddOnForView {
	private final RadioGroup view;
	private OnCheckedChangeListeners onCheckedChangeListeners;

	public RadioGroupAddOn(RadioGroup view) {
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
