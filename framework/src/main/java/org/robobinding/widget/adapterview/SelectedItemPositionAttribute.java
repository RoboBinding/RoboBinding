package org.robobinding.widget.adapterview;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class SelectedItemPositionAttribute implements TwoWayPropertyViewAttribute<AdapterView<?>, Integer>,
	ViewListenersAware<AdapterViewListeners> {
    private AdapterViewListeners adapterViewListeners;

    @Override
    public void setViewListeners(AdapterViewListeners adapterViewListeners) {
	this.adapterViewListeners = adapterViewListeners;
    }

    @Override
    public void updateView(AdapterView<?> view, Integer newPosition) {
        view.setSelection(newPosition);
    }

    @Override
    public void observeChangesOnTheView(AdapterView<?> view, final ValueModel<Integer> valueModel) {
        adapterViewListeners.addOnItemSelectedListener(new OnItemSelectedListener() {
	    @Override
	    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		valueModel.setValue(position);
	    }

	    @Override
	    public void onNothingSelected(AdapterView<?> arg0) {
		valueModel.setValue(AdapterView.INVALID_POSITION);
	    }
	});
    }
}
