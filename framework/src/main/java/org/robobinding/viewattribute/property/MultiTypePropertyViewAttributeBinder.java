package org.robobinding.viewattribute.property;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.ViewAttributeBinder;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class MultiTypePropertyViewAttributeBinder implements ViewAttributeBinder {
	private final PropertyViewAttributeBinderProvider viewAttributeBinderProvider;
	private final ValueModelAttribute attribute;

	private PropertyViewAttributeBinder viewAttributeBinder;

	public MultiTypePropertyViewAttributeBinder(PropertyViewAttributeBinderProvider viewAttributeBinderProvider, 
			ValueModelAttribute attribute) {
		this.viewAttributeBinderProvider = viewAttributeBinderProvider;
		this.attribute = attribute;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		try {
			initializeViewAttributeBinder(bindingContext);
			
			performBind(bindingContext);
		} catch (RuntimeException e) {
			throw new AttributeBindingException(attribute.getName(), e);
		}
	}

	private void initializeViewAttributeBinder(BindingContext presentationModelAdapter) {
		Class<?> propertyType = presentationModelAdapter.getPropertyType(attribute.getPropertyName());
		viewAttributeBinder = viewAttributeBinderProvider.create(propertyType);

		if (viewAttributeBinder == null)
			throw new RuntimeException("Could not find a suitable attribute in " + getClass().getName() + " for property type: " + propertyType);
	}

	private void performBind(BindingContext bindingContext) {
		viewAttributeBinder.bindTo(bindingContext);
	}

	@Override
	public void preInitializeView(BindingContext bindingContext) {
		viewAttributeBinder.preInitializeView(bindingContext);
	}
}
