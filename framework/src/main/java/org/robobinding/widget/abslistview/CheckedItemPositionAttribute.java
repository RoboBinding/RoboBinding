package org.robobinding.widget.abslistview;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widgetaddon.abslistview.AbsListViewAddOn;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CheckedItemPositionAttribute implements TwoWayPropertyViewAttribute<AbsListView, AbsListViewAddOn, Integer> {

	@Override
	public void updateView(AbsListView view, Integer newValue, AbsListViewAddOn viewAddOn) {
		viewAddOn.setItemChecked(newValue, true);
	}

	@Override
	public void observeChangesOnTheView(final AbsListViewAddOn viewAddOn, final ValueModel<Integer> valueModel, AbsListView view) {
		viewAddOn.addOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
				valueModel.setValue(viewAddOn.getCheckedItemPosition());
			}
		});
	}

}
