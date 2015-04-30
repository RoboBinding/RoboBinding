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
public class OnScrollAttribute implements EventViewAttribute<AbsListView>, ViewListenersAware<AbsListViewListeners> {
	private AbsListViewListeners absListViewListeners;

	@Override
	public void setViewListeners(AbsListViewListeners absListViewListeners) {
		this.absListViewListeners = absListViewListeners;

	}

	@Override
	public void bind(AbsListView view, final Command command) {
		absListViewListeners.addOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				ScrollEvent scrollEvent = new ScrollEvent(view, firstVisibleItem, visibleItemCount, totalItemCount);
				command.invoke(scrollEvent);
			}
		});
	}

	@Override
	public Class<? extends AbstractViewEvent> getEventType() {
		return ScrollEvent.class;
	}
}
