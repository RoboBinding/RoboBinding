package org.robobinding.widget.view;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractViewEvent {
	private View view;

	protected AbstractViewEvent(View view) {
		this.view = view;
	}

	public View getView() {
		return view;
	}

}
