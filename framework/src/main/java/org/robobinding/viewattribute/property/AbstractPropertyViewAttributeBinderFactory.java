package org.robobinding.viewattribute.property;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.attribute.ValueModelAttribute;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public abstract class AbstractPropertyViewAttributeBinderFactory {
	private final PropertyAttributeParser propertyAttributeParser;
	
	public AbstractPropertyViewAttributeBinderFactory(PropertyAttributeParser propertyAttributeParser) {
		this.propertyAttributeParser = propertyAttributeParser;
	}
	
	public final PropertyViewAttributeBinder create(Object view, String attributeName, String attributeValue) {
		ValueModelAttribute attribute = propertyAttributeParser.parseAsValueModelAttribute(attributeName, attributeValue);
		return create(view, attribute);
	}
	
	public abstract PropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute);
}
