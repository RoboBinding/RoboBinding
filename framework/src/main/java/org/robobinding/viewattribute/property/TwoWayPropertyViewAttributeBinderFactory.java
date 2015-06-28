package org.robobinding.viewattribute.property;

import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinderFactory.Implementor;
import org.robobinding.widgetaddon.ViewAddOns;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class TwoWayPropertyViewAttributeBinderFactory extends AbstractTwoWayPropertyViewAttributeBinderFactory implements Implementor {
	private final TwoWayPropertyViewAttributeFactory<?> factory;
	
	public TwoWayPropertyViewAttributeBinderFactory(TwoWayPropertyViewAttributeFactory<?> factory, ViewAddOns viewAddOns) {
		super(viewAddOns);
		this.factory = factory;
	}
	
	@Override
	public PropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute) {
		TwoWayPropertyViewAttribute<?, ?, ?> viewAttribute = factory.create();
		return super.create(view, viewAttribute, attribute);
	}
}
