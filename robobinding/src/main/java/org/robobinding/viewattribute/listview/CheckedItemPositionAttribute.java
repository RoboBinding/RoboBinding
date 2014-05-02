package org.robobinding.viewattribute.listview;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.adapterview.AdapterViewListeners;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CheckedItemPositionAttribute extends AbstractPropertyViewAttribute<ListView, Integer> implements
	ViewListenersAware<AdapterViewListeners> {
    private AdapterViewListeners adapterViewListeners;

    @Override
    public void setViewListeners(AdapterViewListeners adapterViewListeners) {
	this.adapterViewListeners = adapterViewListeners;
    }

    @Override
    protected void observeChangesOnTheView(final ValueModel<Integer> valueModel) {
	adapterViewListeners.addOnItemClickListener(new AdapterView.OnItemClickListener() {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
		int checkedItemPosition = view.getCheckedItemPosition();
		valueModel.setValue(checkedItemPosition);
	    }
	});
    }

    @Override
    protected void valueModelUpdated(Integer newValue) {
	view.setItemChecked(newValue, true);
    }
}
