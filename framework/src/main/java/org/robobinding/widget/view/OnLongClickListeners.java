package org.robobinding.widget.view;

import org.robobinding.util.BooleanDecision;
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
	BooleanDecision isConsumed = new BooleanDecision();
	for (OnLongClickListener listener : listeners) {
	    isConsumed.or(listener.onLongClick(v));
	}
	return isConsumed.getResult();
    }

}
