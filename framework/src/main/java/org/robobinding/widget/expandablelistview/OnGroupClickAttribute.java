package org.robobinding.widget.expandablelistview;

import android.view.View;
import android.widget.ExpandableListView;
import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.event.EventViewAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class OnGroupClickAttribute implements EventViewAttribute<ExpandableListView>, ViewListenersAware<ExpandableListViewListeners> {
	private ExpandableListViewListeners adapterViewListeners;

	@Override
	public void setViewListeners(ExpandableListViewListeners adapterViewListeners) {
		this.adapterViewListeners = adapterViewListeners;
	}

	@Override
	public void bind(ExpandableListView view, final Command command) {
		adapterViewListeners.addOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                GroupClickEvent groupClickEvent = new GroupClickEvent(parent, v, groupPosition, id);
                command.invoke(groupClickEvent);
                return true;
            }
        });

	}

	@Override
	public Class<GroupClickEvent> getEventType() {
		return GroupClickEvent.class;
	}

}
