package org.robobinding.supportwidget.swiperefreshlayout;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.event.EventViewAttribute;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

/**
 * 
 * @since
 * @version
 * @author Liang Song
 */
public class OnRefreshAttribute implements
		EventViewAttribute<SwipeRefreshLayout, SwipeRefreshLayoutAddOn> {
	@Override
	public void bind(SwipeRefreshLayoutAddOn viewAddOn, final Command command,
			final SwipeRefreshLayout view) {
		viewAddOn.addOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				RefreshEvent event = new RefreshEvent(view);
				command.invoke(event);
			}
		});
	}

	@Override
	public Class<?> getEventType() {
		return RefreshEvent.class;
	}
}
