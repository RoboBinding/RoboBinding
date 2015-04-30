package org.robobinding.widget.abslistview;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import org.robobinding.widget.adapterview.AdapterViewListeners;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class AbsListViewListeners extends AdapterViewListeners {
	private final AbsListView absListView;
	private OnScrollListeners onScrollListeners;

	public AbsListViewListeners(AbsListView absListView) {
		super(absListView);
		this.absListView = absListView;
	}

	public void addOnScrollListener(OnScrollListener onScrollListener) {
		ensureOnScrollListenersInitialized();
		onScrollListeners.addListener(onScrollListener);
	}

	private void ensureOnScrollListenersInitialized() {
		if (onScrollListeners == null) {
			onScrollListeners = new OnScrollListeners();
			absListView.setOnScrollListener(onScrollListeners);
		}
	}

}
