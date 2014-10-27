package org.robobinding.widget.view;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnFocusAttribute extends AbstractFocusChangeAttribute {
	@Override
	public Class<AbstractViewEvent> getEventType() {
		return AbstractViewEvent.class;
	}

	@Override
	public boolean firesNewEvent(boolean hasFocus) {
		return hasFocus;
	}

	@Override
	public AbstractViewEvent createEvent(View view, boolean hasFocus) {
		return new FocusEvent(view);
	}
}
