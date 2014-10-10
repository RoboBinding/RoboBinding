package org.robobinding.property;

import static org.robobinding.util.Preconditions.checkNotBlank;

import java.text.MessageFormat;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyValidation {
	private final Class<?> beanClass;
	private final Set<String> propertyNames;
	
	public PropertyValidation(Class<?> beanClass, Set<String> propertyNames) {
		this.beanClass = beanClass;
		this.propertyNames = propertyNames;
	}

	public void checkValid(String propertyName) {
		checkNotBlank(propertyName, "propertyName cannot be empty");
		Preconditions.checkArgument(propertyNames.contains(propertyName), 
				MessageFormat.format("No such property ''{0}''", PropertyUtils.shortDescription(beanClass, propertyName)));
	}

}
