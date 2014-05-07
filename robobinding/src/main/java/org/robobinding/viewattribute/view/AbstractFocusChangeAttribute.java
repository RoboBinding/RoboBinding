package org.robobinding.viewattribute.view;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;

import android.view.View;
import android.view.View.OnFocusChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractFocusChangeAttribute extends AbstractCommandViewAttribute<View> implements ViewListenersAware<ViewListeners> {
    private ViewListeners viewListeners;

    @Override
    public void setViewListeners(ViewListeners viewListeners) {
	this.viewListeners = viewListeners;
    }

    @Override
    protected void bind(final Command command) {
	viewListeners.addOnFocusChangeListener(new OnFocusChangeListener() {

	    @Override
	    public void onFocusChange(View view, boolean hasFocus) {
		if (firesNewEvent(hasFocus)) {
		    command.invoke(createEvent(view, hasFocus));
		}
	    }
	});

    }

    @Override
    protected Class<?> getPreferredCommandParameterType() {
	return getEventType();
    }

    protected abstract Class<?> getEventType();

    protected abstract boolean firesNewEvent(boolean hasFocus);

    protected abstract AbstractViewEvent createEvent(View view, boolean hasFocus);
}
