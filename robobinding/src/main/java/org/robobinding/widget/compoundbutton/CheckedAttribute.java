package org.robobinding.widget.compoundbutton;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widget.view.ViewListenersAware;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class CheckedAttribute implements TwoWayPropertyViewAttribute<CompoundButton, Boolean>, ViewListenersAware<CompoundButtonListeners> {
    private CompoundButtonListeners viewListeners;

    @Override
    public void setViewListeners(CompoundButtonListeners viewListeners) {
	this.viewListeners = viewListeners;
    }

    @Override
    public void updateView(CompoundButton view, Boolean newValue) {
        view.setChecked(newValue);
    }

    @Override
    public void observeChangesOnTheView(CompoundButton view, final ValueModel<Boolean> valueModel) {
        viewListeners.addOnCheckedChangeListener(new OnCheckedChangeListener() {
	    @Override
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		valueModel.setValue(isChecked);
	    }
	});
    }
}
