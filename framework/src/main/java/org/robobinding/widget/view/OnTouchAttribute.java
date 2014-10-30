package org.robobinding.widget.view;

import org.robobinding.attribute.Command;
import org.robobinding.widgetaddon.view.ViewAddOnForView;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnTouchAttribute implements EventViewAttributeForView {
	@Override
	public void bind(ViewAddOnForView viewAddOn, final Command command, View view) {
		viewAddOn.addOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				TouchEvent event = new TouchEvent(view, motionEvent);
				Object result = command.invoke(event);
				return Boolean.TRUE.equals(result);
			}
		});
	}

	@Override
	public Class<TouchEvent> getEventType() {
		return TouchEvent.class;
	}
}
