package org.robobinding.widget.adapterview;

import java.util.List;

import org.robobinding.util.RandomValues;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.collect.Lists;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
public class MockArrayAdapter extends ArrayAdapter<String> {
	private static final int NO_LAYOUT = -1;

	public MockArrayAdapter(Context context) {
		super(context, NO_LAYOUT, randamSample());
	}

	private static List<String> randamSample() {
		List<String> items = Lists.newArrayList();
		int numItems = RandomValues.nextInt(10) + 10;
		for (int i = 0; i < numItems; i++) {
			items.add("item " + i);
		}
		return items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView;
		if (convertView == null) {
			textView = new TextView(getContext());
		} else {
			textView = (TextView) convertView;
		}

		textView.setText(getItem(position));
		return textView;
	}

	public void removeLastItem() {
		remove(getItem(getCount() - 1));
	}

}
