package org.robobinding.widget.view;

import org.robobinding.viewattribute.AbstractListeners;

import android.view.View;
import android.view.View.OnLongClickListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnLongClickListeners extends AbstractListeners<OnLongClickListener> implements OnLongClickListener {

    @Override
    public boolean onLongClick(View v) {
	boolean isAnyConsumed = false;
	for (OnLongClickListener listener : listeners) {
	    boolean isConsumed = listener.onLongClick(v);
	    isAnyConsumed = isAnyConsumed || isConsumed;
	}
	return isAnyConsumed;
    }

}
