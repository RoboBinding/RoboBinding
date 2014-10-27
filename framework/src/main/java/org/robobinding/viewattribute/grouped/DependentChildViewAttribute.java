package org.robobinding.viewattribute.grouped;

import org.robobinding.BindingContext;
import org.robobinding.attribute.AbstractAttribute;
import org.robobinding.viewattribute.Bindable;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DependentChildViewAttribute implements Bindable {
	private final ChildViewAttributeFactory factory;
	private final AbstractAttribute attribute;
	private final ChildViewAttributeInitializer viewAttributeInitializer;

	public DependentChildViewAttribute(ChildViewAttributeFactory factory, AbstractAttribute attribute, ChildViewAttributeInitializer viewAttributeInitializer) {
		this.factory = factory;
		this.attribute = attribute;
		this.viewAttributeInitializer = viewAttributeInitializer;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		ChildViewAttribute childViewAttribute = factory.create();
		viewAttributeInitializer.initializeChildViewAttribute(childViewAttribute, attribute);
		childViewAttribute.bindTo(bindingContext);
	}
}