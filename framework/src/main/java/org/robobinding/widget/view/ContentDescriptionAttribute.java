package org.robobinding.widget.view;

import org.robobinding.viewattribute.property.PropertyViewAttribute;

import android.view.View;

public class ContentDescriptionAttribute implements PropertyViewAttribute<View, CharSequence>{

	@Override
	public void updateView(View view, CharSequence newValue) {
		view.setContentDescription(newValue);		
	}
}
