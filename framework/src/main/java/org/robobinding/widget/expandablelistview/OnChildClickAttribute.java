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
public class OnChildClickAttribute implements EventViewAttribute<ExpandableListView>, ViewListenersAware<ExpandableListViewListeners> {
	private ExpandableListViewListeners adapterViewListeners;

	@Override
	public void setViewListeners(ExpandableListViewListeners adapterViewListeners) {
		this.adapterViewListeners = adapterViewListeners;
	}

	@Override
	public void bind(ExpandableListView view, final Command command) {
        adapterViewListeners.addOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ChildClickEvent itemClickEvent = new ChildClickEvent(parent, v, groupPosition, childPosition, id);
                command.invoke(itemClickEvent);
                return true;
            }
        });
	}

	@Override
	public Class<ChildClickEvent> getEventType() {
		return ChildClickEvent.class;
	}

}
