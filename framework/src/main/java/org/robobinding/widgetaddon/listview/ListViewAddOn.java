package org.robobinding.widgetaddon.listview;

import org.robobinding.widgetaddon.abslistview.AbsListViewVariant;
import org.robobinding.widgetaddon.abslistview.AbsListViewAddOn;

import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ListViewAddOn extends AbsListViewAddOn {
	private final ListView view;
	private OnScrollListeners onScrollListeners;
	
	public ListViewAddOn(ListView view, AbsListViewVariant variant) {
		super(view, variant);
		
		this.view = view;
	}

	public void addOnScrollListener(OnScrollListener onScrollListener) {
		ensureOnScrollListenersInitialized();
		onScrollListeners.addListener(onScrollListener);
	}

	private void ensureOnScrollListenersInitialized() {
		if (onScrollListeners == null) {
			onScrollListeners = new OnScrollListeners();
			view.setOnScrollListener(onScrollListeners);
		}
	}
}
