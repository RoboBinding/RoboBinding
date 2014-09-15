package org.robobinding.widget.adapterview;

import android.R;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class MockArrayAdapter extends ArrayAdapter<String> {
	public MockArrayAdapter(Context context) {
		this(context, R.layout.simple_list_item_1);
	}

	public MockArrayAdapter(Context context, int layoutResourceId) {
		super(context, layoutResourceId, Lists.newArrayList("0", "1",
				"2", "3", "4", "5", "6", "7", "8", "9"));
	}

	public void removeLastElement() {
		remove(getItem(getCount() - 1));
	}
}
