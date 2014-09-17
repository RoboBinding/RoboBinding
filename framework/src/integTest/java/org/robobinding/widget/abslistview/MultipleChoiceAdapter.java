package org.robobinding.widget.abslistview;

import org.robobinding.util.RandomValues;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
public class MultipleChoiceAdapter extends AbstractAdapter {
	public MultipleChoiceAdapter(Context context) {
		super(context, RandomValues.nextInt(10) + 10);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CheckedTextView textView;
		if (convertView == null) {
			textView = new CheckedTextView(context);
		} else {
			textView = (CheckedTextView) convertView;
		}

		textView.setText("Item " + position);
		return textView;
	}
}
