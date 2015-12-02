package org.robobinding.supportwidget.swiperefreshlayout;

import org.robobinding.widget.view.AbstractViewEvent;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * 
 * @since
 * @version
 * @author Liang Song
 */
public class RefreshEvent extends AbstractViewEvent {
	public RefreshEvent(SwipeRefreshLayout view) {
		super(view);
	}

	@Override
	public SwipeRefreshLayout getView() {
		return (SwipeRefreshLayout) super.getView();
	}
}
