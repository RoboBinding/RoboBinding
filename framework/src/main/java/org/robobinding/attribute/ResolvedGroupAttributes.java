package org.robobinding.attribute;

import java.util.Map;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ResolvedGroupAttributes {
	private Map<String, AbstractAttribute> resolvedChildAttributes;

	public ResolvedGroupAttributes(Map<String, AbstractAttribute> resolvedChildAttributes) {
		this.resolvedChildAttributes = resolvedChildAttributes;
	}

	public ValueModelAttribute valueModelAttributeFor(String attributeName) {
		return attributeFor(attributeName);
	}

	public StaticResourceAttribute staticResourceAttributeFor(String attributeName) {
		return attributeFor(attributeName);
	}

	public <T extends Enum<T>> EnumAttribute<T> enumAttributeFor(String attributeName) {
		return attributeFor(attributeName);
	}

	public EventAttribute eventAttributeFor(String attributeName) {
		return attributeFor(attributeName);
	}

	public boolean hasAttribute(String attributeName) {
		return resolvedChildAttributes.containsKey(attributeName);
	}

	@SuppressWarnings("unchecked")
	public <AttributeType extends AbstractAttribute> AttributeType attributeFor(String attributeName) {
		return (AttributeType) resolvedChildAttributes.get(attributeName);
	}
}
