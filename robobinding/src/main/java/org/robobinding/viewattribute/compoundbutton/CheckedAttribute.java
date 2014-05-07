package org.robobinding.viewattribute.compoundbutton;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class CheckedAttribute extends AbstractPropertyViewAttribute<CompoundButton, Boolean> implements ViewListenersAware<CompoundButtonListeners> {
    private CompoundButtonListeners viewListeners;

    @Override
    public void setViewListeners(CompoundButtonListeners viewListeners) {
	this.viewListeners = viewListeners;
    }

    @Override
    protected void valueModelUpdated(Boolean newValue) {
	view.setChecked(newValue);
    }

    @Override
    protected void observeChangesOnTheView(final ValueModel<Boolean> valueModel) {
	viewListeners.addOnCheckedChangeListener(new OnCheckedChangeListener() {
	    @Override
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		valueModel.setValue(isChecked);
	    }
	});
    }
}
