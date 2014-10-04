package org.robobinding.codegen;

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

	public void validate(PropertyDescriptor property, Set<String> dependentProperties) {
		validateNotDependsOnSelf(property, dependentProperties);
		validateDependsOnExistingProperties(property, dependentProperties);
	}

	private void validateNotDependsOnSelf(PropertyDescriptor property, Set<String> dependentProperties) {
		if (dependentProperties.contains(property.getName())) {
			throw new RuntimeException(property + " cannot depend on self");
		}
	}

	private void validateDependsOnExistingProperties(PropertyDescriptor property, Set<String> dependentProperties) {
		if (!existingPropertyNames.containsAll(dependentProperties)) {
			List<String> nonExistingDependentProperties = Lists.newArrayList(dependentProperties);
			nonExistingDependentProperties.removeAll(existingPropertyNames);
			throw new RuntimeException(property + " depends on the following non-existent properties '"
					+ Joiner.on(", ").join(nonExistingDependentProperties) + "'");
		}
	}

}
