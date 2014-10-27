package org.robobinding.widget.view;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.event.EventViewAttribute;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class OnClickAttribute implements EventViewAttribute<View>, ViewListenersAware<ViewListenersForView> {
	private ViewListenersForView viewListeners;

	@Override
	public void setViewListeners(ViewListenersForView viewListeners) {
		this.viewListeners = viewListeners;
	}

	@Override
	public void bind(View view, final Command command) {
		viewListeners.addOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ClickEvent clickEvent = new ClickEvent(v);
				command.invoke(clickEvent);
			}
		});
	}

	@Override
	public Class<ClickEvent> getEventType() {
		return ClickEvent.class;
	}
}
