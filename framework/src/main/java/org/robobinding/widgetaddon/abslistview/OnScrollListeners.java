package org.robobinding.widgetaddon.abslistview;

import org.robobinding.widgetaddon.AbstractListeners;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 
 * @author jihunlee
 * 
 */
public class OnScrollListeners extends AbstractListeners<OnScrollListener> implements OnScrollListener {

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		for (OnScrollListener listener : listeners) {
			listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		for (OnScrollListener listener : listeners) {
			listener.onScrollStateChanged(view, scrollState);
		}
	}
}
