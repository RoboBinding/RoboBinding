package org.robobinding.viewattribute.view;

import android.view.View;
import android.view.View.OnFocusChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewListeners {
    View view;
    private OnFocusChangeListeners onFocusChangeListeners;

    public ViewListeners(View view) {
	this.view = view;
    }

    public void addOnFocusChangeListener(OnFocusChangeListener listener) {
	ensureOnFocusChangeListenersInitialized();
	onFocusChangeListeners.addListener(listener);
    }

    private void ensureOnFocusChangeListenersInitialized() {
	if (onFocusChangeListeners == null) {
	    onFocusChangeListeners = new OnFocusChangeListeners();
	    view.setOnFocusChangeListener(onFocusChangeListeners);
	}
    }
}
