package org.robobinding.viewattribute.property;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.attribute.ValueModelAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractMultiTypePropertyViewAttributeBinderFactory {
	private final PropertyAttributeParser propertyAttributeParser;
	
	public AbstractMultiTypePropertyViewAttributeBinderFactory(PropertyAttributeParser propertyAttributeParser) {
		this.propertyAttributeParser = propertyAttributeParser;
	}
	
	public final MultiTypePropertyViewAttributeBinder create(Object view, String attributeName, String attributeValue) {
		ValueModelAttribute attribute = propertyAttributeParser.parseAsValueModelAttribute(attributeName, attributeValue);
		return create(view, attribute);
	}
	
	public abstract MultiTypePropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute);
}
