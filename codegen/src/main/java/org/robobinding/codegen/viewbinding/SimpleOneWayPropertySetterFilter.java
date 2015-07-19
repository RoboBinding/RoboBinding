package org.robobinding.codegen.viewbinding;

import java.util.Collection;

import org.robobinding.codegen.apt.SetterElementFilter;
import org.robobinding.codegen.apt.element.SetterElement;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SimpleOneWayPropertySetterFilter implements SetterElementFilter {
	private final Collection<String> simpleOneWayProperties;

	public SimpleOneWayPropertySetterFilter(Collection<String> simpleOneWayProperties) {
		this.simpleOneWayProperties = simpleOneWayProperties;
	}

	@Override
	public boolean include(SetterElement element) {
		return simpleOneWayProperties.contains(element.propertyName());
	}
}
