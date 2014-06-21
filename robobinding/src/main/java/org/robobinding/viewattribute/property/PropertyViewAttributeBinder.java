package org.robobinding.viewattribute.property;

import org.robobinding.BindingContext;
import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.ViewAttributeBinder;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class PropertyViewAttributeBinder<ViewType extends View, PropertyType> implements ViewAttributeBinder {
    private final boolean withAlwaysPreInitializingView;
    private AbstractBindingProperty<ViewType, PropertyType> bindingProperty;

    public PropertyViewAttributeBinder(AbstractBindingProperty<ViewType, PropertyType> bindingProperty,
	    boolean withAlwaysPreInitializingView) {
	this.bindingProperty = bindingProperty;
	this.withAlwaysPreInitializingView = withAlwaysPreInitializingView;
    }

    @Override
    public void bindTo(BindingContext bindingContext) {
	try {
	    bindingProperty.performBind(bindingContext);
	    if (withAlwaysPreInitializingView) {
		bindingProperty.preInitializeView(bindingContext);
	    }
	} catch (RuntimeException e) {
	    throw new AttributeBindingException(bindingProperty.getAttributeName(), e);
	}
    }

    @Override
    public void preInitializeView(BindingContext bindingContext) {
	if (withAlwaysPreInitializingView) {
	    return;
	}

	try {
	    bindingProperty.preInitializeView(bindingContext);
	} catch (RuntimeException e) {
	    throw new AttributeBindingException(bindingProperty.getAttributeName(), e);
	}
    }
}
