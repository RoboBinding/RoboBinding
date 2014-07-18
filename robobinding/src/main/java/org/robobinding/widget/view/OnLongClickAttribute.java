package org.robobinding.widget.view;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.event.EventViewAttribute;

import android.view.View;
import android.view.View.OnLongClickListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class OnLongClickAttribute implements EventViewAttribute<View>, ViewListenersAware<ViewListeners> {
    private ViewListeners viewListeners;

    @Override
    public void setViewListeners(ViewListeners viewListeners) {
	this.viewListeners = viewListeners;
    }

    @Override
    public void bind(final View view, final Command command) {
	viewListeners.addOnLongClickListener(new OnLongClickListener() {
	    @Override
	    public boolean onLongClick(View v) {
		ClickEvent clickEvent = new ClickEvent(v);
		Object result  = command.invoke(clickEvent);
		return Boolean.TRUE.equals(result);
	    }
	});
    }

    @Override
    public Class<ClickEvent> getEventType() {
	return ClickEvent.class;
    }

}
