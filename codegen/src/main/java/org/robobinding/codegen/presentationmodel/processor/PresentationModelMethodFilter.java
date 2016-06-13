package org.robobinding.codegen.presentationmodel.processor;

import java.util.Set;

import org.robobinding.codegen.apt.MethodElementFilter;
import org.robobinding.codegen.apt.element.MethodElement;
import org.robobinding.util.Sets;

public class PresentationModelMethodFilter implements MethodElementFilter {
	private static final Set<String> filteredMethodNames = Sets.newHashSet("getPresentationModelChangeSupport");

	@Override
	public boolean include(MethodElement element) {
		if (isFilteredMethod(element) || element.isStaticOrNonPublic() || element.hasMoreThanOneParameters()) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isFilteredMethod(MethodElement element) {
		return filteredMethodNames.contains(element.methodName());
	}
}
