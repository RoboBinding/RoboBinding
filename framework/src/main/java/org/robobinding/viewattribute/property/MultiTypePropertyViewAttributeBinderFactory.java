package org.robobinding.viewattribute.property;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.attribute.ValueModelAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MultiTypePropertyViewAttributeBinderFactory {
	private final Implementor implementor;
	private final PropertyAttributeParser propertyAttributeParser;
	
	public MultiTypePropertyViewAttributeBinderFactory(Implementor implementor, PropertyAttributeParser propertyAttributeParser) {
		this.implementor = implementor;
		this.propertyAttributeParser = propertyAttributeParser;
	}
	
	public MultiTypePropertyViewAttributeBinder create(Object view, String attributeName, String attributeValue) {
		ValueModelAttribute attribute = propertyAttributeParser.parseAsValueModelAttribute(attributeName, attributeValue);
		return create(view, attribute);
	}
	
	public MultiTypePropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute) {
		return implementor.create(view, attribute);
	}
	
	public static interface Implementor {
		MultiTypePropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute);
	}
}
