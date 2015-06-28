package org.robobinding.widgetaddon.abslistview;

import android.annotation.TargetApi;
import android.util.SparseBooleanArray;
import android.widget.AbsListView;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@TargetApi(11)
class AbsListView_HoneyComb implements AbsListViewVariant {
	private final AbsListView view;
	
	public AbsListView_HoneyComb(AbsListView view) {
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
