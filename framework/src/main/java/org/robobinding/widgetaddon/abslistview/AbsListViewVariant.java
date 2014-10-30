package org.robobinding.widgetaddon.abslistview;

import android.util.SparseBooleanArray;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface AbsListViewVariant {
	void setItemChecked(int position, boolean value);

	int getCheckedItemPosition();

	void setChoiceMode(int choiceMode);

	SparseBooleanArray getCheckedItemPositions();

	void clearChoices();

}
