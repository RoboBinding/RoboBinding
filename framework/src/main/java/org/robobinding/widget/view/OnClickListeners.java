package org.robobinding.widget.view;

import org.robobinding.viewattribute.AbstractListeners;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnClickListeners extends AbstractListeners<OnClickListener> implements OnClickListener {

	@Override
	public void onClick(View v) {
		for (OnClickListener listener : listeners) {
			listener.onClick(v);
		}
	}

}
