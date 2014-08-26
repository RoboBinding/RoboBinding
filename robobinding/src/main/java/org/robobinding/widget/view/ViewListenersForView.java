package org.robobinding.widget.view;

import org.robobinding.viewattribute.ViewListeners;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewListenersForView implements ViewListeners {
    private final View view;
    private OnClickListeners onClickListeners;
    private OnLongClickListeners onLongClickListeners;
    private OnFocusChangeListeners onFocusChangeListeners;
    private OnTouchListeners onTouchListeners;

    public ViewListenersForView(View view) {
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
    
    public void addOnTouchListener(OnTouchListener listener) {
	ensureOnTouchListenersInitialized();
	onTouchListeners.addListener(listener);
    }
    
    private void ensureOnTouchListenersInitialized() {
	if (onTouchListeners == null) {
	    onTouchListeners = new OnTouchListeners();
	    view.setOnTouchListener(onTouchListeners);
	}
    }
}
