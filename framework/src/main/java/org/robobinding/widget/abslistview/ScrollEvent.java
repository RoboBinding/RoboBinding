package org.robobinding.widget.abslistview;

import org.robobinding.widget.view.AbstractViewEvent;

import android.widget.AbsListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class ScrollEvent extends AbstractViewEvent {

	private int firstVisibleItem;
	private int visibleItemCount;
	private int totalItemCount;

	public ScrollEvent(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
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

	@Override
	public AbsListView getView() {
		return (AbsListView) super.getView();
	}

}
