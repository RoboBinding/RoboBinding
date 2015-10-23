package org.robobinding.attribute;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ValueModelAttribute extends AbstractPropertyAttribute {
	private static final Pattern PROPERTY_ATTRIBUTE_PATTERN = Pattern.compile("[$]?\\{[\\w]+\\}");
	private static final Pattern PROPERTY_NAME_PATTERN = Pattern.compile("\\w+");

	private String propertyName;
	private boolean twoWayBinding;

	public ValueModelAttribute(String name, String value) {
		super(name);
		determinePropertyName(value);
		determineBindingType(value);
	}

	private void determinePropertyName(String value) {
		Matcher matcher = PROPERTY_NAME_PATTERN.matcher(value);
		matcher.find();
		propertyName = matcher.group();
	}

	private void determineBindingType(String value) {
		twoWayBinding = value.startsWith("$");
	}

	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public boolean isTwoWayBinding() {
		return twoWayBinding;
	}
	
	@Override
	public <T> T accept(PropertyAttributeVisitor<T> visitor) {
		return visitor.visitValueModel(this);
	}

	static boolean isValueModelAttribute(String value) {
		Matcher matcher = PROPERTY_ATTRIBUTE_PATTERN.matcher(value);
		return matcher.matches();
	}
}
