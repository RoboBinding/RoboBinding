package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.adapterview.AdapterViewListeners;

import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class ListViewListeners extends AdapterViewListeners {
	private final ListView listView;
	private OnScrollListeners onScrollListeners;

	public ListViewListeners(ListView listView) {
		super(listView);
		this.listView = listView;
	}

	public void addOnScrollListener(OnScrollListener onScrollListener) {
		ensureOnScrollListenersInitialized();
		onScrollListeners.addListener(onScrollListener);
	}

	private void ensureOnScrollListenersInitialized() {
		if (onScrollListeners == null) {
			onScrollListeners = new OnScrollListeners();
			listView.setOnScrollListener(onScrollListeners);
		}
	}

}
