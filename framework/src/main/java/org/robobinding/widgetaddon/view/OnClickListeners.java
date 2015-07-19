package org.robobinding.widgetaddon.view;

import org.robobinding.widgetaddon.AbstractListeners;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class OnClickListeners extends AbstractListeners<OnClickListener> implements OnClickListener {

	@Override
	public void onClick(View v) {
		for (OnClickListener listener : listeners) {
			listener.onClick(v);
		}
	}

}
