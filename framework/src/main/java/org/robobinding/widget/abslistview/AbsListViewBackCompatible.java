package org.robobinding.widget.abslistview;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@TargetApi(11)
public class AbsListViewBackCompatible {
    private AbsListView view;

    public AbsListViewBackCompatible(AbsListView view) {
	this.view = view;
    }

    public void setItemChecked(int position, boolean value) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    view.setItemChecked(position, value);
	} else {
	    ((ListView) view).setItemChecked(position, value);
	}
    }

    public int getCheckedItemPosition() {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    return view.getCheckedItemPosition();
	} else {
	    return ((ListView) view).getCheckedItemPosition();
	}

    }

    public void setChoiceMode(int choiceMode) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    view.setChoiceMode(choiceMode);
	} else {
	    ((ListView) view).setChoiceMode(choiceMode);
	}
    }

    public SparseBooleanArray getCheckedItemPositions() {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    return view.getCheckedItemPositions();
	} else {
	    return ((ListView) view).getCheckedItemPositions();
	}
    }
}
