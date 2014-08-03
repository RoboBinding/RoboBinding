package org.robobinding.widget.listview;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.widget.view.AbstractViewEvent;
import org.robobinding.widget.view.ViewListenersAware;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 *
 * @author jihunlee
 *
 */
public class OnScrollStateChangedAttribute implements EventViewAttribute<ListView>, ViewListenersAware<ListViewListeners> {
    private ListViewListeners listViewListeners;

    @Override
    public void setViewListeners(ListViewListeners viewListeners) {
	this.listViewListeners = viewListeners;

    }
    
    @Override
    public void bind(ListView view, final Command command) {
	listViewListeners.addOnScrollListener(new OnScrollListener() {

	    @Override
	    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	    }

	    @Override
	    public void onScrollStateChanged(AbsListView view, int scrollState) {
		ScrollStateChangedEvent scrollStateChangedEvent = new ScrollStateChangedEvent(view, scrollState);
		command.invoke(scrollStateChangedEvent);
	    }

	});
    }
    
    @Override
    public Class<? extends AbstractViewEvent> getEventType() {
	return ScrollStateChangedEvent.class;
    }
}
