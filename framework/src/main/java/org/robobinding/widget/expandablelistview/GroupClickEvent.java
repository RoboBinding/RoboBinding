package org.robobinding.widget.expandablelistview;

import android.view.View;
import android.widget.ExpandableListView;
import org.robobinding.widget.view.ClickEvent;

/**
 * 
 * @since 1.0
 * @author Jihun Lee
 */
public class GroupClickEvent extends ClickEvent {
	private final ExpandableListView parent;
	private final int groupPosition;
	private final long id;

	GroupClickEvent(ExpandableListView parent, View view, int groupPosition, long id) {
		super(view);
		this.parent = parent;
		this.groupPosition = groupPosition;
		this.id = id;
	}

	public int getGroupPosition() {
		return groupPosition;
	}

	public long getId() {
		return id;
	}

	public ExpandableListView getParent() {
		return parent;
	}
}
