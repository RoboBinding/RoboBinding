package org.robobinding.widget.adapterview;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.widget.view.ViewListenersAware;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnItemClickAttribute implements EventViewAttribute<AdapterView<?>>, ViewListenersAware<AdapterViewListeners> {
    private AdapterViewListeners adapterViewListeners;

    @Override
    public void setViewListeners(AdapterViewListeners adapterViewListeners) {
	this.adapterViewListeners = adapterViewListeners;
    }

    @Override
    public void bind(AdapterView<?> view, final Command command) {
        adapterViewListeners.addOnItemClickListener(new OnItemClickListener() {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ItemClickEvent itemClickEvent = new ItemClickEvent(parent, view, position, id);
		command.invoke(itemClickEvent);
	    }
	});
    }

    @Override
    public Class<ItemClickEvent> getEventType() {
        return ItemClickEvent.class;
    }

}
