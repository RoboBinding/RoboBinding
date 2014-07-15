package org.robobinding.widget.radiogroup;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.widget.view.ViewListenersAware;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnCheckedChangeAttribute implements EventViewAttribute<RadioGroup>, ViewListenersAware<RadioGroupListeners> {
    private RadioGroupListeners radioGroupListeners;
    
    @Override
    public void bind(RadioGroup view, final Command command) {
	radioGroupListeners.addOnCheckedChangeListener(new OnCheckedChangeListener() {
	    
	    @Override
	    public void onCheckedChanged(RadioGroup group, int checkedId) {
		CheckedChangeEvent event = new CheckedChangeEvent(group, checkedId);
		command.invoke(event);
	    }
	});
    }
    
    @Override
    public Class<CheckedChangeEvent> getEventType() {
        return CheckedChangeEvent.class;
    }
    
    @Override
    public void setViewListeners(RadioGroupListeners viewListeners) {
	this.radioGroupListeners = viewListeners;
    }
}
