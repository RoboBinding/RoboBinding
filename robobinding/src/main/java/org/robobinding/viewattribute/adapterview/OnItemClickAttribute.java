package org.robobinding.viewattribute.adapterview;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnItemClickAttribute extends AbstractCommandViewAttribute<AdapterView<?>> implements ViewListenersAware<AdapterViewListeners> {
    private AdapterViewListeners adapterViewListeners;

    @Override
    public void setViewListeners(AdapterViewListeners adapterViewListeners) {
	this.adapterViewListeners = adapterViewListeners;
    }

    @Override
    protected void bind(final Command command) {
	adapterViewListeners.addOnItemClickListener(new OnItemClickListener() {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ItemClickEvent itemClickEvent = new ItemClickEvent(parent, view, position, id);
		command.invoke(itemClickEvent);
	    }
	});
    }

    @Override
    protected Class<?> getPreferredCommandParameterType() {
	return ItemClickEvent.class;
    }

}
