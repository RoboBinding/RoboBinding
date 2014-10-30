package org.robobinding.widget.view;

import org.robobinding.attribute.Command;
import org.robobinding.widgetaddon.view.ViewAddOnForView;

import android.view.View;
import android.view.View.OnLongClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class OnLongClickAttribute implements EventViewAttributeForView {
	@Override
	public void bind(ViewAddOnForView viewAddOn, final Command command, View view) {
		viewAddOn.addOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				ClickEvent clickEvent = new ClickEvent(v);
				Object result = command.invoke(clickEvent);
				return Boolean.TRUE.equals(result);
			}
		});
	}

	@Override
	public Class<ClickEvent> getEventType() {
		return ClickEvent.class;
	}

}
