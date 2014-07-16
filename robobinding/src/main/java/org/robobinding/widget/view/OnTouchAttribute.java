package org.robobinding.widget.view;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.event.EventViewAttribute;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnTouchAttribute implements EventViewAttribute<View>, ViewListenersAware<ViewListeners> {
    private ViewListeners viewListeners;

    @Override
    public void setViewListeners(ViewListeners viewListeners) {
	this.viewListeners = viewListeners;
    }

    @Override
    public void bind(View view, final Command command) {
	viewListeners.addOnTouchListener(new OnTouchListener() {
	    
	    @Override
	    public boolean onTouch(View view, MotionEvent motionEvent) {
		TouchEvent event = new TouchEvent(view, motionEvent);
		command.invoke(event);
		return true;
	    }
	});
    }

    @Override
    public Class<TouchEvent> getEventType() {
	return TouchEvent.class;
    }
}
