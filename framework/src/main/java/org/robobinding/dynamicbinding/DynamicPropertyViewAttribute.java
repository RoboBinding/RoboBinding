package org.robobinding.dynamicbinding;

import org.robobinding.property.PropertyDescriptor.Setter;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DynamicPropertyViewAttribute<T extends View> implements PropertyViewAttribute<T, Object> {
	private final Setter viewPropertySetter;

	public DynamicPropertyViewAttribute(Setter viewPropertySetter) {
		this.viewPropertySetter = viewPropertySetter;
	}

	@Override
	public void updateView(T view, Object newValue) {
		viewPropertySetter.doSet(view, newValue);
	}
}
