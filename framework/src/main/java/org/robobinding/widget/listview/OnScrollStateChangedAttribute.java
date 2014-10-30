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
 */
public class OnScrollStateChangedAttribute implements EventViewAttributeForListView {
	@Override
	public void bind(ListViewAddOn viewAddOn, final Command command, ListView view) {
		viewAddOn.addOnScrollListener(new OnScrollListener() {

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
