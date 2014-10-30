package org.robobinding.widgetaddon.abslistview;

import org.robobinding.widgetaddon.adapterview.AdapterViewAddOn;

import android.util.SparseBooleanArray;
import android.widget.AbsListView;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class AbsListViewAddOn extends AdapterViewAddOn {
	private final AbsListViewVariant variant;
	
	protected AbsListViewAddOn(AbsListView view, AbsListViewVariant variant) {
		super(view);
		
		this.variant = variant;
	}
	
	public void setItemChecked(int position, boolean value){
		variant.setItemChecked(position, value);
	}

	public int getCheckedItemPosition() {
		return variant.getCheckedItemPosition();
	}

	public void setChoiceMode(int choiceMode) {
		variant.setChoiceMode(choiceMode);
	}

	public SparseBooleanArray getCheckedItemPositions() {
		return variant.getCheckedItemPositions();
	}

	public void clearChoices() {
		variant.clearChoices();
	}
}
