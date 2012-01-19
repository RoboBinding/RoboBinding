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
package org.robobinding.property;

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
public class DependencyTest
{
	private ObservableTestBean observableTestBean;
	private PresentationModelPropertyChangeListener listener;
	@Before
	public void setUp()
	{
		observableTestBean = new ObservableTestBean();
		listener = new MockPresentationModelPropertyChangeListener();
	}
	@Test
	public void whenCreateWithValidDependentProperties_thenSuccessful()
	{
		createDependency(ObservableTestBean.PROPERTY_WITH_VALID_DEPENDENT_PROPERTIES);
	}
	@Test(expected=RuntimeException.class)
	public void whenCreateWithDependingOnSelf_thenThrowException()
	{
		createDependency(ObservableTestBean.PROPERTY_DEPENDING_ON_SELF);
	}
	@Test(expected=RuntimeException.class)
	public void whenCreateWithSomeNonExistingDependentProperties_thenThrowException()
	{
		createDependency(ObservableTestBean.PROPERTY_WITH_SOME_NONEXISTING_DEPENDENT_PROPERTIES);
	}
	public void givenDependency_whenAddListenerToDependentProperties_thenListenersAddedCorrectly()
	{
		Dependency dependency = createDependency(ObservableTestBean.PROPERTY_WITH_VALID_DEPENDENT_PROPERTIES);
		
		dependency.addListenerToDependentProperties(listener);
		
		assertHasListenersToProperties(ObservableTestBean.DEPENDENT_PROPERTY, ObservableTestBean.PROPERTY);
	}
	private void assertHasListenersToProperties(String... propertyNames)
	{
		for(String propertyName : propertyNames)
		{
			Assert.assertTrue(observableTestBean.hasPropertyChangeListener(propertyName, listener));
		}
	}
	@Test
	public void givenDependencyAndListenerToItsDependentProperties_whenRemoveListener_thenListenerRemovedOffDependentProperties()
	{
		Dependency dependency = createDependency(ObservableTestBean.PROPERTY_WITH_VALID_DEPENDENT_PROPERTIES);
		dependency.addListenerToDependentProperties(listener);
		
		dependency.removeListenerOffDependentProperties(listener);
		
		assertHasNoListenersToProperties(ObservableTestBean.DEPENDENT_PROPERTY, ObservableTestBean.PROPERTY);
	}
	private void assertHasNoListenersToProperties(String... propertyNames)
	{
		for(String propertyName : propertyNames)
		{
			Assert.assertFalse(observableTestBean.hasPropertyChangeListener(propertyName, listener));
		}
	}
	@Test
	public void givenDependencyWithDuplicatedDependentProperties_whenAddListener_thenListenerAddedToDependentPropertyOnceOnly()
	{
		Dependency dependency = createDependency(ObservableTestBean.PROPERTY_WITH_DUPLICATED_DEPENDENT_PROPERTIES);
		
		dependency.addListenerToDependentProperties(listener);
		
		assertListenerRegisteredNumTimes(ObservableTestBean.DEPENDENT_PROPERTY, 1);
	}
	private void assertListenerRegisteredNumTimes(String propertyName, int numTimes)
	{
		observableTestBean.isPropertyChangeListenerRegisteredNumTimes(propertyName, listener, numTimes);
	}
	private Dependency createDependency(String propertyName)
	{
		PropertyAccessor<Boolean> propertyAccessor = PropertyAccessorUtils.createPropertyAccessor(observableTestBean.getClass(), propertyName);
		ObservableBean observableBean = new ObservableBean(observableTestBean);
		Dependency dependency = new Dependency(
				observableBean, 
				propertyAccessor, 
				getAvailablePropertyNames());
		return dependency;
	}
	private List<String> getAvailablePropertyNames()
	{
		return PropertyUtils.getPropertyNames(ObservableTestBean.class);
	}
}
