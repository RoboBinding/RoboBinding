package org.robobinding.widgetaddon.adapterview;

import org.robobinding.widgetaddon.AbstractListeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class OnItemSelectedListeners extends AbstractListeners<OnItemSelectedListener> implements OnItemSelectedListener {
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		for (OnItemSelectedListener listener : listeners) {
			listener.onItemSelected(parent, view, position, id);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		for (OnItemSelectedListener listener : listeners) {
			listener.onNothingSelected(parent);
		}
	}

	public static OnItemSelectedListeners convert(OnItemSelectedListener listener) {
		if (listener instanceof OnItemSelectedListeners) {
			return (OnItemSelectedListeners) listener;
		} else {
			OnItemSelectedListeners onItemSelectedListeners = new OnItemSelectedListeners();
			onItemSelectedListeners.addListener(listener);
			return onItemSelectedListeners;
		}
	}

}
