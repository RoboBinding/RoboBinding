package org.robobinding.widget.view;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.view.View;

public class ContentDescriptionAttribute implements OneWayPropertyViewAttribute<View, CharSequence>{

	@Override
	public void updateView(View view, CharSequence newValue) {
		view.setContentDescription(newValue);		
	}
}
