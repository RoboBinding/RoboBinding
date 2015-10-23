package org.robobinding.attribute;

import static org.robobinding.attribute.StaticResourceAttribute.isStaticResourceAttribute;
import static org.robobinding.attribute.StaticResourcesAttribute.isStaticResourcesAttribute;
import static org.robobinding.attribute.ValueModelAttribute.isValueModelAttribute;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class PropertyAttributeParser {
	public AbstractPropertyAttribute parse(String name, String value) {
		if (isValueModelAttribute(value)) {
			return new ValueModelAttribute(name, value);
		} else if (isStaticResourcesAttribute(value)) {
			return new StaticResourcesAttribute(name, value);
		} else if (isStaticResourceAttribute(value)) {
			return new StaticResourceAttribute(name, value);
		}

		throw new MalformedAttributeException(name, describeSyntaxError(value));
	}

	public ValueModelAttribute parseAsValueModelAttribute(String name, String value) {
		if (isValueModelAttribute(value)) {
			return new ValueModelAttribute(name, value);
		}

		throw new MalformedAttributeException(name, describeSyntaxError(value));
	}

	StaticResourceAttribute parseAsStaticResourceAttribute(String name, String value) {
		if (isStaticResourceAttribute(value)) {
			return new StaticResourceAttribute(name, value);
		}

		throw new MalformedAttributeException(name, describeSyntaxError(value));
	}

	private String describeSyntaxError(String value) {
		String errorMessage = "Invalid binding syntax: '" + value + "'.";

		Matcher propertyBindingAttempted = Pattern.compile("\\{.+|.+\\}").matcher(value);
		Matcher resourceBindingAttempted = Pattern.compile(".*@.*").matcher(value);

		if (!propertyBindingAttempted.matches())
			errorMessage += "\n\nDid you mean '{" + value + "}' or '${" + value + "}'? (one/two-way binding)\n";
		else if (resourceBindingAttempted.matches())
			errorMessage += "\n\nDid you mean to omit the curly braces? (Curly braces are for binding to a property on the presentation model, "
					+ "not static resources)";

		return errorMessage;
	}
}
