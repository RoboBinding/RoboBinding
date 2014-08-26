package org.robobinding.widget.listview;

import org.robobinding.widget.view.AbstractViewEvent;

import android.widget.AbsListView;

/**
 *
 * @author jihunlee
 *
 */
public class ScrollStateChangedEvent extends AbstractViewEvent {
    private int scrollState;

    ScrollStateChangedEvent(AbsListView view, int scrollState) {
	super(view);
	this.scrollState = scrollState;
    }

    public int getScrollState() {
	return scrollState;
    }

    @Override
    public AbsListView getView() {
        return (AbsListView) super.getView();
    }
}
