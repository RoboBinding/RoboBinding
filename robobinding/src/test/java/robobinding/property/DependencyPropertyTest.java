/**
 * DependencyPropertyTest.java
 * Oct 29, 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.property;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DependencyPropertyTest
{
	private ObservableBean observableBean;
	private PresentationModelPropertyChangeListener listener;
	@Before
	public void setUp()
	{
		observableBean = new ObservableBean();
		listener = new PresentationModelPropertyChangeListenerImpl();
	}
	@Test
	public void whenCreatePropertyWithValidDependentProperties_thenSuccessful()
	{
		createDependencyProperty(ObservableBean.PROPERTY_WITH_VALID_DEPENDENT_PROPERTIES);
	}
	@Test(expected=RuntimeException.class)
	public void whenCreatePropertyDependingOnSelf_thenThrowException()
	{
		createDependencyProperty(ObservableBean.PROPERTY_DEPENDING_ON_SELF);
	}
	@Test(expected=RuntimeException.class)
	public void whenCreatePropertyWithSomeNonExistingDependentProperties_thenThrowException()
	{
		createDependencyProperty(ObservableBean.PROPERTY_WITH_SOME_NONEXISTING_DEPENDENT_PROPERTIES);
	}
	public void givenDependentProperties_whenAddValueChangeListener_thenListenerAddedToDependentProperties()
	{
		AbstractProperty<Boolean> property = createDependencyProperty(ObservableBean.PROPERTY_WITH_VALID_DEPENDENT_PROPERTIES);
		
		property.addPropertyChangeListener(listener);
		
		assertHasListenersToProperties(ObservableBean.ANNOTATED_PROPERTY, ObservableBean.PROPERTY);
	}
	private void assertHasListenersToProperties(String... propertyNames)
	{
		for(String propertyName : propertyNames)
		{
			Assert.assertTrue(observableBean.hasPropertyChangeListener(propertyName, listener));
		}
	}
	@Test
	public void givenDuplicatedDependentProperties_whenAddValueChangeListener__thenNoDuplicatedListenerAddedToDependentProperties()
	{
		AbstractProperty<Boolean> property = createDependencyProperty(ObservableBean.PROPERTY_WITH_DUPLICATED_DEPENDENT_PROPERTIES);
		
		property.addPropertyChangeListener(listener);
		
		assertListenerRegisteredNumTimes(ObservableBean.PROPERTY, 1);
	}
	private void assertListenerRegisteredNumTimes(String propertyName, int numTimes)
	{
		observableBean.isPropertyChangeListenerRegisteredNumTimes(propertyName, listener, numTimes);
	}
	@Test
	public void givenPropertyAndExistingValueChangeListenerToDependentProperties_whenRemoveListener_thenListenerRemoveOffDependentProperties()
	{
		AbstractProperty<Boolean> property = createDependencyProperty(ObservableBean.PROPERTY_WITH_VALID_DEPENDENT_PROPERTIES);
		addListenerToProperties(ObservableBean.ANNOTATED_PROPERTY, ObservableBean.PROPERTY);
		
		property.removePropertyChangeListener(listener);
		
		assertHasNoListenersToProperties(ObservableBean.ANNOTATED_PROPERTY, ObservableBean.PROPERTY);
	}
	private void assertHasNoListenersToProperties(String... propertyNames)
	{
		for(String propertyName : propertyNames)
		{
			Assert.assertFalse(observableBean.hasPropertyChangeListener(propertyName, listener));
		}
	}
	private void addListenerToProperties(String... propertyNames)
	{
		for(String propertyName : propertyNames)
		{
			observableBean.addPropertyChangeListener(propertyName, listener);
		}
	}
	private DependencyProperty<Boolean> createDependencyProperty(String propertyName)
	{
		PropertyAccessor<Boolean> propertyAccessor = PropertyAccessorUtils.createPropertyAccessor(observableBean.getClass(), propertyName);
		DependencyProperty<Boolean> dependencyProperty = new DependencyProperty<Boolean>(
				observableBean, 
				propertyAccessor, 
				getAvailablePropertyNames());
		return dependencyProperty;
	}
	private List<String> getAvailablePropertyNames()
	{
		return PropertyUtils.getPropertyNames(ObservableBean.class);
	}
}
