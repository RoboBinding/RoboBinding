package org.robobinding.viewattribute.adapterview;

import android.R;
import android.app.Activity;
import android.widget.ArrayAdapter;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class MockArrayAdapter extends ArrayAdapter<String> {
    public MockArrayAdapter() {
	this(R.layout.simple_list_item_1);
    }

    public MockArrayAdapter(int layoutResourceId) {
	super(new Activity(), layoutResourceId, Lists.newArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
    }

    public void removeLastElement() {
	remove(getItem(getCount() - 1));
    }
}
