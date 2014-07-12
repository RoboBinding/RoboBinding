package org.robobinding.widget.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewListeners {
    private final View view;
    private OnClickListeners onClickListeners;
    private OnLongClickListeners onLongClickListeners;
    private OnFocusChangeListeners onFocusChangeListeners;

    public ViewListeners(View view) {
	this.view = view;
    }

    public void addOnClickListener(OnClickListener listener) {
	ensureOnClickListenersInitialized();
	onClickListeners.addListener(listener);
    }

    private void ensureOnClickListenersInitialized() {
	if (onClickListeners == null) {
	    onClickListeners = new OnClickListeners();
	    view.setOnClickListener(onClickListeners);
	}
    }

    public void addOnLongClickListener(OnLongClickListener listener) {
	ensureOnLongClickListenersInitialized();
	onLongClickListeners.addListener(listener);
    }

    private void ensureOnLongClickListenersInitialized() {
	if (onLongClickListeners == null) {
	    onLongClickListeners = new OnLongClickListeners();
	    view.setOnLongClickListener(onLongClickListeners);
	}
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
