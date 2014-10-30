package org.robobinding.widget.compoundbutton;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.widgetaddon.compoundbutton.CompoundButtonAddOn;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnCheckedChangeAttribute implements EventViewAttribute<CompoundButton, CompoundButtonAddOn> {
	@Override
	public void bind(CompoundButtonAddOn viewAddOn, final Command command, CompoundButton view) {
		viewAddOn.addOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				CheckedChangeEvent event = new CheckedChangeEvent(buttonView, isChecked);
				command.invoke(event);
			}
		});
	}

	@Override
	public Class<CheckedChangeEvent> getEventType() {
		return CheckedChangeEvent.class;
	}
}
