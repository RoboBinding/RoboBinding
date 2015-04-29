package org.robobinding.widget.abslistview;

import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockAbsListViewListeners extends AbsListViewListeners {
	public boolean addOnScrollListenerInvoked = false;

	public MockAbsListViewListeners(ListView view) {
		super(view);
	}

	@Override
	public void addOnScrollListener(OnScrollListener onScrollListener) {
		addOnScrollListenerInvoked = true;
		super.addOnScrollListener(onScrollListener);
	}
}
