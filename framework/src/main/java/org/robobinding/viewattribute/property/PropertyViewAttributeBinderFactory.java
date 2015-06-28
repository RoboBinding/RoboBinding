package org.robobinding.viewattribute.property;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.attribute.ValueModelAttribute;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyViewAttributeBinderFactory {
	private final Implementor implementor;
	private final PropertyAttributeParser propertyAttributeParser;
	
	public PropertyViewAttributeBinderFactory(Implementor implementor, PropertyAttributeParser propertyAttributeParser) {
		this.implementor = implementor;
		this.propertyAttributeParser = propertyAttributeParser;
	}
	
	public PropertyViewAttributeBinder create(Object view, String attributeName, String attributeValue) {
		ValueModelAttribute attribute = propertyAttributeParser.parseAsValueModelAttribute(attributeName, attributeValue);
		return create(view, attribute);
	}
	
	public PropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute) {
		return implementor.create(view, attribute);
	}
	
	public static interface Implementor {
		PropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute);
	}
}
