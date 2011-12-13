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

import org.robobinding.DependsOnStateOf;
import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.internal.com_google_common.collect.Sets;
import org.robobinding.internal.org_apache_commons_lang3.StringUtils;



/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DependencyProperty<T> extends AbstractProperty<T>
{
	private Set<String> dependentProperties;
	public DependencyProperty(Object bean, PropertyAccessor<T> propertyAccessor, Collection<String> availablePropertyNames)
	{
		super(bean, propertyAccessor);
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
	private void validateDependsOnExistingProperties(Collection<String> availablePropertyNames)
	{
		if(!availablePropertyNames.containsAll(dependentProperties))
		{
			List<String> nonExistingDependentProperties = Lists.newArrayList(dependentProperties);
			nonExistingDependentProperties.removeAll(availablePropertyNames);
			throw new RuntimeException(toString()+" depends on the following non-existent properties '"+StringUtils.join(nonExistingDependentProperties, ",")+"'");
		}
	}
	private void validateNotDependsOnSelf()
	{
		if(dependentProperties.contains(propertyAccessor.getPropertyName()))
		{
			throw new RuntimeException(toString()+" cannot depend on self");
		}
	}
	@Override
	public void addPropertyChangeListener(PresentationModelPropertyChangeListener listener)
	{
		super.addPropertyChangeListener(listener);
		addListenerToDependentProperties(listener);
	}
	@Override
	public void removePropertyChangeListener(PresentationModelPropertyChangeListener listener)
	{
		super.removePropertyChangeListener(listener);
		removeListenerOffDependentProperties(listener);
	}
	private void addListenerToDependentProperties(PresentationModelPropertyChangeListener listener)
	{
		ObservableProperties observableBean = getObservableBean();
		for(String dependentProperty : dependentProperties)
		{
			observableBean.addPropertyChangeListener(dependentProperty, listener);
		}
	}
	private void removeListenerOffDependentProperties(PresentationModelPropertyChangeListener listener)
	{
		if(isObservableBean())
		{
			ObservableProperties observableBean = getObservableBean();
			for(String dependentProperty : dependentProperties)
			{
				observableBean.removePropertyChangeListener(dependentProperty, listener);
			}
		}
	}
	@Override
	public String toString()
	{
		String dependencyDescription =  "dependentProperties:["+StringUtils.join(dependentProperties, ",")+"]";
		return propertyAccessor.toString(dependencyDescription);
	}
}
