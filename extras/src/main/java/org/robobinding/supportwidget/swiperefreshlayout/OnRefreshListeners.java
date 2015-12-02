package org.robobinding.supportwidget.swiperefreshlayout;

import org.robobinding.widgetaddon.AbstractListeners;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

/**
 * 
 * @since
 * @version
 * @author Liang Song
 */
public class OnRefreshListeners extends AbstractListeners<OnRefreshListener>
		implements OnRefreshListener {
	@Override
	public void onRefresh() {
		for (OnRefreshListener onRefreshListener : listeners)
			onRefreshListener.onRefresh();
	}
}
