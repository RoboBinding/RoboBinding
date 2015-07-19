package org.robobinding.widget.adapterview;

import org.robobinding.attribute.Command;
import org.robobinding.widgetaddon.adapterview.AdapterViewAddOn;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnItemSelectedAttribute implements EventViewAttributeForAdapterView {
	@Override
	public void bind(AdapterViewAddOn viewAddOn, final Command command, AdapterView<?> view) {
		viewAddOn.addOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				ItemClickEvent itemClickEvent = new ItemClickEvent(parent, view, position, id);
				command.invoke(itemClickEvent);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				ItemClickEvent itemClickEvent = new ItemClickEvent(parent, null, AdapterView.INVALID_POSITION, 0);
				command.invoke(itemClickEvent);
			}
		});
		// TODO: looks unnecessary implementation.
		/*
		 * view.getAdapter().registerDataSetObserver(new DataSetObserver() {
		 * public void onChanged() { int count = view.getAdapter().getCount();
		 * 
		 * int position = (count>0) ?
		 * view.getSelectedItemPosition():AdapterView.INVALID_POSITION;
		 * 
		 * if (position >= count) return;
		 * 
		 * ItemClickEvent itemClickEvent = new ItemClickEvent(view,
		 * view.getChildAt(position), position, 0);
		 * command.invoke(itemClickEvent); } });
		 */
	}

	@Override
	public Class<ItemClickEvent> getEventType() {
		return ItemClickEvent.class;
	}
}
