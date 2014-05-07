package org.robobinding.viewattribute.adapterview;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class SelectedItemPositionAttribute extends AbstractPropertyViewAttribute<AdapterView<?>, Integer> implements
	ViewListenersAware<AdapterViewListeners> {
    private AdapterViewListeners adapterViewListeners;

    @Override
    public void setViewListeners(AdapterViewListeners adapterViewListeners) {
	this.adapterViewListeners = adapterViewListeners;
    }

    @Override
    protected void valueModelUpdated(Integer newPosition) {
	view.setSelection(newPosition);
    }

    @Override
    protected void observeChangesOnTheView(final ValueModel<Integer> valueModel) {
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
