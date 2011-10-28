/**
 * DependencyProperty.java
 * Oct 27, 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.beans;

import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import robobinding.presentationmodel.DependsOn;
import robobinding.presentationmodel.ObservableProperties;
import robobinding.utils.StringUtils;

import com.google.common.collect.Lists;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DependencyProperty<T> extends AbstractProperty<T>
{
	private List<String> dependentProperties;
	public DependencyProperty(Object bean, PropertyAccessor<T> propertyAccessor, Collection<String> availablePropertyNames)
	{
		super(bean, propertyAccessor);
		initializeDependentProperties();
		validateDependentProperties(availablePropertyNames);
	}
	private void initializeDependentProperties()
	{
		DependsOn dependsOn = propertyAccessor.getAnnotation(DependsOn.class);
		dependentProperties = Lists.newArrayList(dependsOn.value());
	}
	private void validateDependentProperties(Collection<String> availablePropertyNames)
	{
		if(!availablePropertyNames.containsAll(dependentProperties))
		{
			List<String> nonExistingDependentProperties = Lists.newArrayList(dependentProperties);
			nonExistingDependentProperties.removeAll(availablePropertyNames);
			throw new RuntimeException(toString()+" depends on following non-existing properties '"+StringUtils.join(nonExistingDependentProperties, ",")+"'");
		}
	}
	@Override
	public void addValueChangeListener(PropertyChangeListener listener)
	{
		super.addValueChangeListener(listener);
		addListenerToDependentProperties(listener);
	}
	@Override
	public void removeValueChangeListener(PropertyChangeListener listener)
	{
		super.removeValueChangeListener(listener);
		removeListenerToDependentProperties(listener);
	}
	private void addListenerToDependentProperties(PropertyChangeListener listener)
	{
		ObservableProperties observableBean = getObservableBean();
		for(String dependentProperty : dependentProperties)
		{
			observableBean.addPropertyChangeListener(dependentProperty, listener);
		}
	}
	private void removeListenerToDependentProperties(PropertyChangeListener listener)
	{
		ObservableProperties observableBean = getObservableBean();
		for(String dependentProperty : dependentProperties)
		{
			observableBean.removePropertyChangeListener(dependentProperty, listener);
		}
	}
	@Override
	public String toString()
	{
		Object value = propertyAccessor.safeGetValue(bean);
		String beanType = bean.getClass().getName();
		String setter = propertyAccessor.safeGetWriteMethodName();
		String getter = propertyAccessor.safeGetReadMethodName();
		return MessageFormat.format(
				"property(name:{0}, value:{1}, propertyType:{2}, dependentProperties:[{3}], setter:{4}, getter:{5}, beanType:{6})", 
				propertyAccessor.getPropertyName(), 
				value, 
				propertyAccessor.getPropertyType(),
				StringUtils.join(dependentProperties, ","),
				setter, 
				getter, 
				beanType);

	}
}
