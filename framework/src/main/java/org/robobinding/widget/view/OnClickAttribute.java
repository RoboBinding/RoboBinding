package org.robobinding.widget.view;

import org.robobinding.attribute.Command;
import org.robobinding.widgetaddon.view.ViewAddOnForView;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class OnClickAttribute implements EventViewAttributeForView {
	
	@Override
	public void bind(ViewAddOnForView viewAddOn, final Command command, View view) {
		viewAddOn.addOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ClickEvent clickEvent = new ClickEvent(v);
				command.invoke(clickEvent);
			}
		});
	}

	@Override
	public Class<ClickEvent> getEventType() {
		return ClickEvent.class;
	}
}
