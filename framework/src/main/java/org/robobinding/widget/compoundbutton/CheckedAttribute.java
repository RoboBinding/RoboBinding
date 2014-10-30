package org.robobinding.widget.compoundbutton;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widgetaddon.compoundbutton.CompoundButtonAddOn;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class CheckedAttribute implements TwoWayPropertyViewAttribute<CompoundButton, CompoundButtonAddOn, Boolean> {
	@Override
	public void updateView(CompoundButton view, Boolean newValue, CompoundButtonAddOn viewAddOn) {
		view.setChecked(newValue);
	}

	@Override
	public void observeChangesOnTheView(CompoundButtonAddOn viewAddOn, final ValueModel<Boolean> valueModel, CompoundButton view) {
		viewAddOn.addOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				valueModel.setValue(isChecked);
			}
		});
	}
}
