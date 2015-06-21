package org.robobinding.widget.abslistview;

import org.robobinding.attribute.Command;
import org.robobinding.widget.view.AbstractViewEvent;
import org.robobinding.widgetaddon.abslistview.AbsListViewAddOn;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 
 * @since 1.0
 * @author jihunlee
 * @author Cheng Wei
 * 
 */
public class OnScrollAttribute implements EventViewAttributeForAbsListView {
	@Override
	public void bind(AbsListViewAddOn viewAddOn, final Command command, AbsListView view) {
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
