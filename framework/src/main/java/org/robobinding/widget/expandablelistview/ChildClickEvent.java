package org.robobinding.widget.expandablelistview;

import android.view.View;
import android.widget.ExpandableListView;
import org.robobinding.widget.view.ClickEvent;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class ChildClickEvent extends ClickEvent {
	private final ExpandableListView parent;
	private final int groupPosition;
    private final int childPosition;
	private final long id;

	ChildClickEvent(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
		super(view);
		this.parent = parent;
		this.groupPosition = groupPosition;
        this.childPosition = childPosition;
		this.id = id;
	}

	public int getGroupPosition() {
		return groupPosition;
	}

    public int getChildPosition() {
        return childPosition;
    }

	public long getId() {
		return id;
	}

	public ExpandableListView getParent() {
		return parent;
	}
}
