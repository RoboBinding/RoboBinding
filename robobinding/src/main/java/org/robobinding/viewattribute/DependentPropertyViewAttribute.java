package org.robobinding.viewattribute;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DependentPropertyViewAttribute implements Bindable {
    private final ViewAttributeFactory<? extends PropertyViewAttribute<? extends View>> factory;
    private final ValueModelAttribute attribute;
    private final ChildViewAttributeInitializer viewAttributeInitializer;

    public DependentPropertyViewAttribute(
	    ViewAttributeFactory<? extends PropertyViewAttribute<? extends View>> factory,
	    ValueModelAttribute attribute,
	    ChildViewAttributeInitializer viewAttributeInitializer) {
	this.factory = factory;
	this.attribute = attribute;
	this.viewAttributeInitializer = viewAttributeInitializer;
    }

    @Override
    public void bindTo(BindingContext bindingContext) {
	PropertyViewAttribute<? extends View> propertyViewAttribute = factory.create();
	viewAttributeInitializer.initializePropertyViewAttribute(propertyViewAttribute, attribute);
	propertyViewAttribute.bindTo(bindingContext);
    }
}