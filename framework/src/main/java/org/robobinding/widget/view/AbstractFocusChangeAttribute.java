package org.robobinding.widget.view;

import org.robobinding.attribute.Command;
import org.robobinding.widgetaddon.view.ViewAddOnForView;

import android.view.View;
import android.view.View.OnFocusChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractFocusChangeAttribute implements EventViewAttributeForView {
	@Override
	public void bind(ViewAddOnForView viewAddOn, final Command command, View view) {
		viewAddOn.addOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				if (firesNewEvent(hasFocus)) {
					command.invoke(createEvent(view, hasFocus));
				}
			}
		});

	}

	protected abstract boolean firesNewEvent(boolean hasFocus);

	protected abstract AbstractViewEvent createEvent(View view, boolean hasFocus);
}
