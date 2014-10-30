package org.robobinding.viewattribute.property;

import org.robobinding.BindingContext;
import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.ViewAttributeBinder;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class PropertyViewAttributeBinder implements ViewAttributeBinder {
	private final AbstractBindingProperty bindingProperty;
	private final String attributeName;

	public PropertyViewAttributeBinder(AbstractBindingProperty bindingProperty, 
			String attributeName) {
		this.bindingProperty = bindingProperty;
		this.attributeName = attributeName;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		try {
			bindingProperty.performBind(bindingContext);
			if (bindingProperty.isAlwaysPreInitializingView()) {
				bindingProperty.preInitializeView(bindingContext);
			}
		} catch (RuntimeException e) {
			throw new AttributeBindingException(attributeName, e);
		}
	}

	@Override
	public void preInitializeView(BindingContext bindingContext) {
		if (bindingProperty.isAlwaysPreInitializingView()) {
			return;
		}

		try {
			bindingProperty.preInitializeView(bindingContext);
		} catch (RuntimeException e) {
			throw new AttributeBindingException(attributeName, e);
		}
	}
}
