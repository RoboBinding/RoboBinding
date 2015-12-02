package org.robobinding.supportwidget.swiperefreshlayout;

import org.robobinding.widgetaddon.view.ViewAddOnForView;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

/**
 * 
 * @since
 * @version
 * @author Liang Song
 */
public class SwipeRefreshLayoutAddOn extends ViewAddOnForView {
	private final SwipeRefreshLayout view;
	private OnRefreshListeners onRefreshListeners;

	public SwipeRefreshLayoutAddOn(SwipeRefreshLayout view) {
		super(view);
		this.view = view;
	}

	public void addOnRefreshListener(OnRefreshListener listener) {
		ensureOnRefreshListenersInitialized();
		onRefreshListeners.addListener(listener);
	}

	private void ensureOnRefreshListenersInitialized() {
		if (onRefreshListeners == null) {
			onRefreshListeners = new OnRefreshListeners();
			view.setOnRefreshListener(onRefreshListeners);
		}
	}
}
