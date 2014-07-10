package org.robobinding.viewattribute.listview;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * 
 * @author jihunlee
 * 
 */
public class OnScrollStateChangedAttribute
		extends
			AbstractCommandViewAttribute<ListView>
		implements
			ViewListenersAware<ListViewListeners> {
	private ListViewListeners listViewListeners;

	@Override
	public void setViewListeners(ListViewListeners viewListeners) {
		this.listViewListeners = viewListeners;

	}

	@Override
	protected void bind(final Command command) {
		listViewListeners.addOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				ScrollStateChangedEvent scrollStateChangedEvent = new ScrollStateChangedEvent(
						view, scrollState);
				command.invoke(scrollStateChangedEvent);
			}

		});

	}

	@Override
	protected Class<?> getPreferredCommandParameterType() {
		return ScrollStateChangedEvent.class;
	}

}
