package org.robobinding.widget.view;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.event.EventViewAttribute;

import android.view.View;
import android.view.View.OnFocusChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractFocusChangeAttribute implements EventViewAttribute<View>, ViewListenersAware<ViewListenersForView> {
    private ViewListenersForView viewListeners;

    @Override
    public void setViewListeners(ViewListenersForView viewListeners) {
	this.viewListeners = viewListeners;
    }

    @Override
    public void bind(final View view, final Command command) {
	viewListeners.addOnFocusChangeListener(new OnFocusChangeListener() {

	    @Override
	    public void onFocusChange(View view, boolean hasFocus) {
		if (firesNewEvent(hasFocus)) {
		    command.invoke(createEvent(view, hasFocus));
		}
	    }
	});

    }

    protected abstract boolean firesNewEvent(boolean hasFocus);

    protected abstract AbstractViewEvent createEvent(View view, boolean hasFocus);
}
