package org.robobinding.widget.abslistview;

import org.robolectric.Robolectric;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
public class SingleChoiceAdapter extends AbstractAdapter {
	public SingleChoiceAdapter() {
		super(10);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = new TextView(Robolectric.application);
		textView.setText("Item " + position);
		return textView;
	}
}