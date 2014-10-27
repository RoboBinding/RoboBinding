package org.robobinding.widget.view;

import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TouchEvent extends AbstractViewEvent {
	private MotionEvent motionEvent;

	public TouchEvent(View view, MotionEvent motionEvent) {
		super(view);
		this.motionEvent = motionEvent;
	}

	public MotionEvent getMotionEvent() {
		return motionEvent;
	}
}
