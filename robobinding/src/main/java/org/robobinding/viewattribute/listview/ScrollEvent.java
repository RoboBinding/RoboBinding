package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.view.AbstractViewEvent;

import android.widget.AbsListView;

public class ScrollEvent extends AbstractViewEvent {

	private int firstVisibleItem;
	private int visibleItemCount;
	private int totalItemCount;

	ScrollEvent(AbsListView view, int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {
		super(view);

		this.firstVisibleItem = firstVisibleItem;
		this.visibleItemCount = visibleItemCount;
		this.totalItemCount = totalItemCount;

	}

	public int getFirstVisibleItem() {
		return firstVisibleItem;
	}

	public int getVisibleItemCount() {
		return visibleItemCount;
	}

	public int getTotalItemCount() {
		return totalItemCount;
	}

}
