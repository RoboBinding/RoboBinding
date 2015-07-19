package org.robobinding.widgetaddon.abslistview;

import android.util.SparseBooleanArray;
import android.widget.ListView;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
class AbsListView_Froyo implements AbsListViewVariant {
	private ListView view;
	
	public AbsListView_Froyo(ListView view) {
		this.view = view;
	}
	
	@Override
	public void setItemChecked(int position, boolean value) {
		view.setItemChecked(position, value);
	}

	@Override
	public int getCheckedItemPosition() {
		return view.getCheckedItemPosition();

	}

	@Override
	public SparseBooleanArray getCheckedItemPositions() {
		return view.getCheckedItemPositions();
	}

	@Override
	public void clearChoices() {
		view.clearChoices();
	}
}
