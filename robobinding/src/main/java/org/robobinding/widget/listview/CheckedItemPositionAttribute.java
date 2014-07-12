package org.robobinding.widget.listview;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widget.adapterview.AdapterViewListeners;
import org.robobinding.widget.view.ViewListenersAware;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CheckedItemPositionAttribute implements TwoWayPropertyViewAttribute<ListView, Integer>, ViewListenersAware<AdapterViewListeners> {
    private AdapterViewListeners adapterViewListeners;

    @Override
    public void setViewListeners(AdapterViewListeners adapterViewListeners) {
	this.adapterViewListeners = adapterViewListeners;
    }

    @Override
    public void updateView(ListView view, Integer newValue) {
	view.setItemChecked(newValue, true);
    }

    @Override
    public void observeChangesOnTheView(final ListView view, final ValueModel<Integer> valueModel) {
        adapterViewListeners.addOnItemClickListener(new AdapterView.OnItemClickListener() {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
		int checkedItemPosition = view.getCheckedItemPosition();
		valueModel.setValue(checkedItemPosition);
	    }
	});
    }

}
