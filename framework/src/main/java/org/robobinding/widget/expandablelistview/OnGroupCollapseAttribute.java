package org.robobinding.widget.expandablelistview;

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
public class OnGroupCollapseAttribute implements EventViewAttribute<ExpandableListView>, ViewListenersAware<ExpandableListViewListeners> {
	private ExpandableListViewListeners adapterViewListeners;

	@Override
	public void setViewListeners(ExpandableListViewListeners adapterViewListeners) {
		this.adapterViewListeners = adapterViewListeners;
	}

	@Override
	public void bind(final ExpandableListView view, final Command command) {
        adapterViewListeners.addOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener(){

            @Override
            public void onGroupCollapse(int groupPosition) {
                GroupCollapseEvent groupCollapseEvent = new GroupCollapseEvent(groupPosition);
                command.invoke(groupCollapseEvent);
            }
        });
	}

	@Override
	public Class<GroupCollapseEvent> getEventType() {
		return GroupCollapseEvent.class;
	}
}
