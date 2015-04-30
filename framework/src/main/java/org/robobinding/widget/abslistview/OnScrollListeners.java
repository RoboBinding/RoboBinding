package org.robobinding.widget.abslistview;

import org.robobinding.viewattribute.AbstractListeners;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
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
