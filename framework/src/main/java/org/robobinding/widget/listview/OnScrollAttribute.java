package org.robobinding.widget.listview;

import org.robobinding.attribute.Command;
import org.robobinding.widget.view.AbstractViewEvent;
import org.robobinding.widgetaddon.listview.ListViewAddOn;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @author jihunlee
 * @author Cheng Wei
 * 
 */
public class OnScrollAttribute implements EventViewAttributeForListView {
	@Override
	public void bind(ListViewAddOn viewAddOn, final Command command, ListView view) {
		viewAddOn.addOnScrollListener(new OnScrollListener() {

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
