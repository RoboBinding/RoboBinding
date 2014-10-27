package org.robobinding.codegen.processor;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DependencyValidation {
	private final Set<String> existingPropertyNames;

	public DependencyValidation(Set<String> existingPropertyNames) {
		this.existingPropertyNames = existingPropertyNames;
	}

	public void validate(String property, Set<String> dependentProperties) {
		validateNotDependsOnSelf(property, dependentProperties);
		validateDependsOnExistingProperties(property, dependentProperties);
	}

	private void validateNotDependsOnSelf(String property, Set<String> dependentProperties) {
		if (dependentProperties.contains(property)) {
			throw new RuntimeException("The property '" +property+ "' cannot depend on self");
		}
	}

	private void validateDependsOnExistingProperties(String property, Set<String> dependentProperties) {
		if (!existingPropertyNames.containsAll(dependentProperties)) {
			List<String> nonExistingDependentProperties = Lists.newArrayList(dependentProperties);
			nonExistingDependentProperties.removeAll(existingPropertyNames);
			throw new RuntimeException(MessageFormat.format("The property ''{0}'' depends on the following non-existent properties ''{1}''", 
					property, Joiner.on(", ").join(nonExistingDependentProperties)));
		}
	}

}
