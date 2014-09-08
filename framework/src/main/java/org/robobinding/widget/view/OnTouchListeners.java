package org.robobinding.widget.view;

import org.robobinding.util.BooleanDecision;
import org.robobinding.viewattribute.AbstractListeners;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnTouchListeners extends AbstractListeners<OnTouchListener> implements OnTouchListener {
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
	BooleanDecision isConsumed = new BooleanDecision();
	for (OnTouchListener listener : listeners) {
	    isConsumed.or(listener.onTouch(v, event));
	}
	return isConsumed.getResult();
    }

}
