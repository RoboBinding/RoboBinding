package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.view.AbstractViewEvent;

import android.widget.AbsListView;

public class ScrollStateChangedEvent extends AbstractViewEvent {
	private int scrollState;
	private AbsListView view;

	ScrollStateChangedEvent(AbsListView view, int scrollState) {
		super(view);
		this.scrollState = scrollState;
		this.view = view;
	}

	public int getScrollState() {
		return scrollState;
	}

	@Override
	public AbsListView getView() {
		return view;
	}

	
}
