package org.robobinding.viewattribute.edittext;

import org.robobinding.viewattribute.view.AbstractViewEvent;

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

    TextChangedEvent(TextView textView, int start, int before, int count) {
	super(textView);
	this.start = start;
	this.before = before;
	this.count = count;
    }

    public TextView getTextView() {
	return (TextView) getView();
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
