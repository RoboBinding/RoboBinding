package org.robobinding.widget.abslistview;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widget.adapterview.AdapterViewListeners;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

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
		new AbsListViewBackCompatible(view).setItemChecked(newValue, true);
	}

	@Override
	public void observeChangesOnTheView(final AbsListView view, final ValueModel<Integer> valueModel) {
		adapterViewListeners.addOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
				int checkedItemPosition = new AbsListViewBackCompatible(view).getCheckedItemPosition();
				valueModel.setValue(checkedItemPosition);
			}
		});
	}

}
