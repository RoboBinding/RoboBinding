package org.robobinding.codegen.viewbinding;

import java.text.MessageFormat;

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
	
	public static OneWayBindingPropertyGenerationException noSetterFound(Class<?> viewClass, String propertyName) {
		return new OneWayBindingPropertyGenerationException(
				MessageFormat.format("No public accessible unique setter ''{0}.{1}()'' with a single parameter found", 
						viewClass.getName(), PropertyUtils.setterNameOf(propertyName)));
	}
}
