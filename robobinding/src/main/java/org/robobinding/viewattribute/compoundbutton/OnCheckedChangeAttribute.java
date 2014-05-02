package org.robobinding.viewattribute.compoundbutton;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnCheckedChangeAttribute extends AbstractCommandViewAttribute<CompoundButton> implements ViewListenersAware<CompoundButtonListeners> {
    private CompoundButtonListeners viewListeners;

    @Override
    public void setViewListeners(CompoundButtonListeners viewListeners) {
	this.viewListeners = viewListeners;
    }

    @Override
    protected void bind(final Command command) {
	viewListeners.addOnCheckedChangeListener(new OnCheckedChangeListener() {

	    @Override
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		CheckedChangeEvent event = new CheckedChangeEvent(buttonView, isChecked);
		command.invoke(event);
	    }
	});
    }

    @Override
    protected Class<?> getPreferredCommandParameterType() {
	return CheckedChangeEvent.class;
    }
}
