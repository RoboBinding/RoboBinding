package org.robobinding.widgetaddon.adapterview;

import org.robobinding.widgetaddon.AbstractListeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class OnItemClickListeners extends AbstractListeners<OnItemClickListener> implements OnItemClickListener {
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		for (OnItemClickListener listener : listeners) {
			listener.onItemClick(parent, view, position, id);
		}
	}

	public static OnItemClickListeners convert(OnItemClickListener listener) {
		if (listener instanceof OnItemClickListeners) {
			return (OnItemClickListeners) listener;
		} else {
			OnItemClickListeners onItemClickListeners = new OnItemClickListeners();
			onItemClickListeners.addListener(listener);
			return onItemClickListeners;
		}
	}

}
