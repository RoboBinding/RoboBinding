package org.robobinding.widget.radiogroup;

import org.robobinding.widget.view.ViewListeners;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class RadioGroupListeners extends ViewListeners {
    private final RadioGroup view;
    private OnCheckedChangeListeners onCheckedChangeListeners;

    public RadioGroupListeners(RadioGroup view) {
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
