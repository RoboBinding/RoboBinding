package org.robobinding.widget.abslistview;

import org.robobinding.util.RandomValues;

import android.content.Context;
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
	public SingleChoiceAdapter(Context context) {
		super(context, RandomValues.nextInt(10) + 10);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView;
		if (convertView == null) {
			textView = new TextView(context);
		} else {
			textView = (TextView) convertView;
		}

		textView.setText("Item " + position);
		return textView;
	}
}