package org.robobinding.viewattribute.grouped;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.widgetaddon.ViewAddOnInjector;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewAttributeBinderFactories {
	private final PropertyAttributeParser propertyAttributeParser;
	private final GroupAttributesResolver groupAttributesResolver;
	private final ViewAddOnInjector viewAddOnInjector;

	public ViewAttributeBinderFactories(PropertyAttributeParser propertyAttributeParser, 
			GroupAttributesResolver groupAttributesResolver,
			ViewAddOnInjector viewAddOnInjector) {
		this.propertyAttributeParser = propertyAttributeParser;
		this.groupAttributesResolver = groupAttributesResolver;
		this.viewAddOnInjector = viewAddOnInjector;
	}
	
	public ViewAttributeBinderFactory create(Object view) {
		return new ViewAttributeBinderFactory(view, propertyAttributeParser, groupAttributesResolver, viewAddOnInjector);
	}
	
	
}
