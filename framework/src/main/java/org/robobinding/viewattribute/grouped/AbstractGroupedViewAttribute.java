package org.robobinding.viewattribute.grouped;

import org.robobinding.BindingContext;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractGroupedViewAttribute<T extends View> implements GroupedViewAttribute<T> {
	private static final String[] NO_COMPULSORY_ATTRIBUTES = new String[0];

	@Override
	public String[] getCompulsoryAttributes() {
		return NO_COMPULSORY_ATTRIBUTES;
	}

	@Override
	public void postBind(T view, final BindingContext bindingContext) {
	}
}
