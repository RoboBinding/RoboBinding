package org.robobinding.codegen;

import java.util.List;
import java.util.Set;

import org.robobinding.property.PropertyAccessor;

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

	public void validate(PropertyAccessor propertyAccessor, Set<String> dependentProperties) {
		validateNotDependsOnSelf(propertyAccessor, dependentProperties);
		validateDependsOnExistingProperties(propertyAccessor, dependentProperties);
	}

	private void validateNotDependsOnSelf(PropertyAccessor propertyAccessor, Set<String> dependentProperties) {
		if (dependentProperties.contains(propertyAccessor.getPropertyName())) {
			throw new RuntimeException(propertyAccessor.getShortDescription() + " cannot depend on self");
		}
	}

	private void validateDependsOnExistingProperties(PropertyAccessor propertyAccessor, Set<String> dependentProperties) {
		if (!existingPropertyNames.containsAll(dependentProperties)) {
			List<String> nonExistingDependentProperties = Lists.newArrayList(dependentProperties);
			nonExistingDependentProperties.removeAll(existingPropertyNames);
			throw new RuntimeException(propertyAccessor.getShortDescription() + " depends on the following non-existent properties '"
					+ Joiner.on(", ").join(nonExistingDependentProperties) + "'");
		}
	}

}
