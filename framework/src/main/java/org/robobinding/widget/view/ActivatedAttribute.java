package org.robobinding.widget.view;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.annotation.TargetApi;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
public class ActivatedAttribute implements OneWayPropertyViewAttribute<View, Boolean> {
	@TargetApi(11)
	@Override
	public void updateView(View view, Boolean newValue) {
		view.setActivated(newValue);
	}
}
