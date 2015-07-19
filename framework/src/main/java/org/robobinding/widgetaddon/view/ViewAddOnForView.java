package org.robobinding.widgetaddon.view;

import org.robobinding.widgetaddon.ViewAddOn;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewAddOnForView implements ViewAddOn {
	private final View view;
	
	private OnClickListeners onClickListeners;
	private OnLongClickListeners onLongClickListeners;
	private OnFocusChangeListeners onFocusChangeListeners;
	private OnTouchListeners onTouchListeners;

	public ViewAddOnForView(View view) {
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
