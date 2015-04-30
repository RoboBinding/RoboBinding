package org.robobinding.widget.abslistview;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.widget.view.AbstractViewEvent;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class OnScrollStateChangedAttribute implements EventViewAttribute<AbsListView>, ViewListenersAware<AbsListViewListeners> {
	private AbsListViewListeners absListViewListeners;

	@Override
	public void setViewListeners(AbsListViewListeners viewListeners) {
		this.absListViewListeners = viewListeners;

	}

	@Override
	public void bind(AbsListView view, final Command command) {
		absListViewListeners.addOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				ScrollStateChangedEvent scrollStateChangedEvent = new ScrollStateChangedEvent(view, scrollState);
				command.invoke(scrollStateChangedEvent);
			}

		});
	}

	@Override
	public Class<? extends AbstractViewEvent> getEventType() {
		return ScrollStateChangedEvent.class;
	}
}
