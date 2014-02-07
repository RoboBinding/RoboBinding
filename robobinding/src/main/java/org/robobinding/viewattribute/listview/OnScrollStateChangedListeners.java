package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.AbstractListeners;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class OnScrollStateChangedListeners
		extends
			AbstractListeners<OnScrollListener> implements OnScrollListener {

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {


	}

	public static OnScrollListeners convert(OnScrollListener listener) {
		if (listener instanceof OnScrollListeners) {
			return (OnScrollListeners) listener;
		} else {
			OnScrollListeners onScrollListeners = new OnScrollListeners();
			onScrollListeners.addListener(listener);
			return onScrollListeners;
		}
	}

}
