package org.robobinding.codegen.viewbinding;

import java.text.MessageFormat;
import java.util.Collection;

import org.robobinding.codegen.apt.element.WrappedTypeElement;
import org.robobinding.util.Joiner;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class OneWayBindingPropertyGenerationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OneWayBindingPropertyGenerationException(String message) {
		super(message);
	}
	
	public static OneWayBindingPropertyGenerationException noSettersFound(WrappedTypeElement viewType, Collection<String> properties) {
		return new OneWayBindingPropertyGenerationException(
				MessageFormat.format("No public accessible setters with a single parameter found for properties: ''{0}.{1}''", 
						viewType.qName(), Joiner.on("/").join(properties)));
	}
	
	public static OneWayBindingPropertyGenerationException setterWithDifferentParameterTypes(WrappedTypeElement viewType, 
			Collection<String> propertyNames) {
		return new OneWayBindingPropertyGenerationException(
				MessageFormat.format("The properties ''{0}.{1}'' have multiple setters with different parameter types, which cannot be auto-generated", 
						viewType.qName(), Joiner.on("/").join(propertyNames)));
	}
}
