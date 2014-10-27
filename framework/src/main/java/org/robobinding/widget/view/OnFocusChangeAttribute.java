package org.robobinding.widget.view;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnFocusChangeAttribute extends AbstractFocusChangeAttribute {
	@Override
	public Class<FocusChangeEvent> getEventType() {
		return FocusChangeEvent.class;
	}

	@Override
	public boolean firesNewEvent(boolean hasFocus) {
		return true;
	}

	@Override
	public AbstractViewEvent createEvent(View view, boolean hasFocus) {
		return new FocusChangeEvent(view, hasFocus);
	}
}
