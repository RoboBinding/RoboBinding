package org.robobinding.widget.adapterview;

import org.robobinding.attribute.Command;
import org.robobinding.widgetaddon.adapterview.AdapterViewAddOn;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnItemClickAttribute implements EventViewAttributeForAdapterView {
	@Override
	public void bind(AdapterViewAddOn viewAddOn, final Command command, AdapterView<?> view) {
		viewAddOn.addOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ItemClickEvent itemClickEvent = new ItemClickEvent(parent, view, position, id);
				command.invoke(itemClickEvent);
			}
		});
	}

	@Override
	public Class<ItemClickEvent> getEventType() {
		return ItemClickEvent.class;
	}

}
