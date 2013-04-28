/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.property;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.robobinding.presentationmodel.DependsOnStateOf;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class Dependency
{
	private ObservableBean observableBean;
	private Set<String> dependentProperties;
	private PropertyAccessor<?> propertyAccessor;

	public Dependency(ObservableBean observableBean, PropertyAccessor<?> propertyAccessor, Collection<String> availablePropertyNames)
	{
		this.observableBean = observableBean;
		this.propertyAccessor = propertyAccessor;
		initializeDependentProperties();
		validateDependentProperties(availablePropertyNames);
	}

	private void initializeDependentProperties()
	{
		DependsOnStateOf dependsOn = propertyAccessor.getAnnotation(DependsOnStateOf.class);
		dependentProperties = Sets.newHashSet(dependsOn.value());
	}

	private void validateDependentProperties(Collection<String> availablePropertyNames)
	{
		validateNotDependsOnSelf();
		validateDependsOnExistingProperties(availablePropertyNames);
	}

	private void validateNotDependsOnSelf()
	{
		if (dependentProperties.contains(propertyAccessor.getPropertyName()))
		{
			throw new RuntimeException(propertyAccessor.propertyDescription() + " cannot depend on self");
		}
	}

	private void validateDependsOnExistingProperties(Collection<String> availablePropertyNames)
	{
		if (!availablePropertyNames.containsAll(dependentProperties))
		{
			List<String> nonExistingDependentProperties = Lists.newArrayList(dependentProperties);
			nonExistingDependentProperties.removeAll(availablePropertyNames);
			throw new RuntimeException(propertyAccessor.propertyDescription() + " depends on the following non-existent properties '"
					+ Joiner.on(", ").join(nonExistingDependentProperties) + "'");
		}
	}

	public void addListenerToDependentProperties(PresentationModelPropertyChangeListener listener)
	{
		for (String dependentProperty : dependentProperties)
		{
			observableBean.addPropertyChangeListener(dependentProperty, listener);
		}
	}

	public void removeListenerOffDependentProperties(PresentationModelPropertyChangeListener listener)
	{
		for (String dependentProperty : dependentProperties)
		{
			observableBean.removePropertyChangeListener(dependentProperty, listener);
		}
	}

	private String describeDependentProperties()
	{
		return "dependentProperties:[" + Joiner.on(",").join(dependentProperties) + "]";
	}
	
	public String decribeDependencyProperty()
	{
		String dependencyDescription = describeDependentProperties();
		return propertyAccessor.toString(dependencyDescription);
	}
}