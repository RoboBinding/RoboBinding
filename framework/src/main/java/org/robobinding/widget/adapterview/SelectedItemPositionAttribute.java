package org.robobinding.widget.adapterview;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widgetaddon.adapterview.AdapterViewAddOn;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class SelectedItemPositionAttribute implements TwoWayPropertyViewAttribute<AdapterView<?>, AdapterViewAddOn, Integer> {

	@Override
	public void updateView(AdapterView<?> view, Integer newPosition, AdapterViewAddOn viewAddOn) {
		view.setSelection(newPosition);
	}

	@Override
	public void observeChangesOnTheView(AdapterViewAddOn viewAddOn, final ValueModel<Integer> valueModel, AdapterView<?> view) {
		viewAddOn.addOnItemSelectedListener(new OnItemSelectedListener() {
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
