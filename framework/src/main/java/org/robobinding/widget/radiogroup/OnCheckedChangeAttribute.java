package org.robobinding.widget.radiogroup;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.widgetaddon.radiogroup.RadioGroupAddOn;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnCheckedChangeAttribute implements EventViewAttribute<RadioGroup, RadioGroupAddOn> {
	@Override
	public void bind(RadioGroupAddOn viewAddOn, final Command command, RadioGroup view) {
		viewAddOn.addOnCheckedChangeListener(new OnCheckedChangeListener() {

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
}
