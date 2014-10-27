package org.robobinding.attribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class EnumAttribute<T extends Enum<T>> extends AbstractAttribute {
	private T enumeratedValue;

	public EnumAttribute(String attribute, String attributeValue, Class<T> enumClass) {
		super(attribute);
		enumeratedValue = determineAttributeValue(attributeValue, enumClass);
	}

	private T determineAttributeValue(String attributeValue, Class<T> enumClass) {
		for (T value : enumClass.getEnumConstants()) {
			if (value.toString().equals(attributeValue))
				return value;
		}

		throw new MalformedAttributeException(getName(), "Invalid " + getName() + " attribute value: " + attributeValue);
	}

	public T getValue() {
		return enumeratedValue;
	}

}
