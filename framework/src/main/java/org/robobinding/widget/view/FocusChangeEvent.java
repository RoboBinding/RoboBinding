package org.robobinding.widget.view;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class FocusChangeEvent extends AbstractViewEvent {
	private boolean hasFocus;

	public FocusChangeEvent(View view, boolean hasFocus) {
		super(view);
		this.hasFocus = hasFocus;
	}

	public boolean isHasFocus() {
		return hasFocus;
	}
}
