package org.robobinding.widget.abslistview;

import org.robolectric.Robolectric;

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
	public MultipleChoiceAdapter() {
		super(10);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CheckedTextView textView = new CheckedTextView(Robolectric.application);
		textView.setText("Item " + position);
		return textView;
	}
}
