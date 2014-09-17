package org.robobinding.dynamicbinding;

import org.robobinding.property.PropertyDescriptor.Setter;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DynamicPropertyViewAttributeFactory<T extends View> implements PropertyViewAttributeFactory<T> {
	private final Setter viewPropertySetter;

	public DynamicPropertyViewAttributeFactory(Setter viewPropertySetter) {
		this.viewPropertySetter = viewPropertySetter;
	}

	@Override
	public PropertyViewAttribute<T, ?> create() {
		return new DynamicPropertyViewAttribute<T>(viewPropertySetter);
	}

}
