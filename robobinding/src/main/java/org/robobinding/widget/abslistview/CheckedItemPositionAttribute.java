package org.robobinding.widget.abslistview;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widget.adapterview.AdapterViewListeners;
import org.robobinding.widget.view.ViewListenersAware;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AbsListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CheckedItemPositionAttribute implements TwoWayPropertyViewAttribute<AbsListView, Integer>, ViewListenersAware<AdapterViewListeners> {
    private AdapterViewListeners adapterViewListeners;

    @Override
    public void setViewListeners(AdapterViewListeners adapterViewListeners) {
	this.adapterViewListeners = adapterViewListeners;
    }

    @Override
    public void updateView(AbsListView view, Integer newValue) {
	view.setItemChecked(newValue, true);
    }

    @Override
    public void observeChangesOnTheView(final AbsListView view, final ValueModel<Integer> valueModel) {
        adapterViewListeners.addOnItemClickListener(new AdapterView.OnItemClickListener() {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
		int checkedItemPosition = view.getCheckedItemPosition();
		valueModel.setValue(checkedItemPosition);
	    }
	});
    }

}
