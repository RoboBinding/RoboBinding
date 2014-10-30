package org.robobinding.widgetaddon.view;

import org.robobinding.widgetaddon.AbstractListeners;

import android.view.View;
import android.view.View.OnFocusChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class OnFocusChangeListeners extends AbstractListeners<OnFocusChangeListener> implements OnFocusChangeListener {
	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		for (OnFocusChangeListener listener : listeners) {
			listener.onFocusChange(view, hasFocus);
		}
	}
}
