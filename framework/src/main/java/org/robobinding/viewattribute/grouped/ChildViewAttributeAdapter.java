package org.robobinding.viewattribute.grouped;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinderFactory;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildViewAttributeAdapter<T extends View> implements ChildViewAttribute {
    private PropertyViewAttributeBinder<T, ?> viewAttributeBinder;

    public ChildViewAttributeAdapter(T view, PropertyViewAttribute<T, ?> viewAttribute, ValueModelAttribute attribute) {
	PropertyViewAttributeBinderFactory<T> viewAttributeBinderFactory = new PropertyViewAttributeBinderFactory<T>(view);
	viewAttributeBinder = viewAttributeBinderFactory.create(viewAttribute, attribute);
    }

    @Override
    public void bindTo(BindingContext bindingContext) {
	viewAttributeBinder.bindTo(bindingContext);
    }
}
