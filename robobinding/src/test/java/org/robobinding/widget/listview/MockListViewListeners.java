package org.robobinding.widget.listview;

import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockListViewListeners extends ListViewListeners {
    public boolean addOnScrollListenerInvoked = false;

    public MockListViewListeners(ListView view) {
	super(view);
    }

    @Override
    public void addOnScrollListener(OnScrollListener onScrollListener) {
	addOnScrollListenerInvoked = true;
        super.addOnScrollListener(onScrollListener);
    }
}
