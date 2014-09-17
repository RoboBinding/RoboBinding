package org.robobinding.widget.edittext;

import org.robobinding.widget.view.AbstractViewEvent;

import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TextChangedEvent extends AbstractViewEvent {
	private int start;
	private int before;
	private int count;

	public TextChangedEvent(TextView textView, int start, int before, int count) {
		super(textView);
		this.start = start;
		this.before = before;
		this.count = count;
	}

	@Override
	public TextView getView() {
		return (TextView) super.getView();
	}

	public int getStart() {
		return start;
	}

	public int getBefore() {
		return before;
	}

	public int getCount() {
		return count;
	}
}
