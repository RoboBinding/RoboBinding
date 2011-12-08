/**
 * AbstractPropertyTest.java
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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import robobinding.property.AbstractProperty;
import robobinding.property.PropertyAccessor;



/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class AbstractPropertyTest
{
	private PresentationModelPropertyChangeListener listener;
	@Before
	public void setUp()
	{
		listener = new PresentationModelPropertyChangeListenerImpl();
	}
	@Test
	public void testGivenObservableBean_whenAddPropertyChangeListener_thenListenerAdded()
	{
		ObservableBean observableBean = new ObservableBean();
		AbstractProperty<Boolean> property = createProperty(observableBean);
		
		property.addPropertyChangeListener(listener);
		
		assertHasListener(observableBean);
	}
	private void assertHasListener(ObservableBean observableBean)
	{
		Assert.assertTrue(observableBean.hasPropertyChangeListener(Bean.PROPERTY, listener));
	}
	@Test(expected=RuntimeException.class)
	public void testGivenNonObservableBean_whenAddPropertyChangeListener_thenThrowException()
	{
		Bean bean = new Bean();
		AbstractProperty<Boolean> property = createProperty(bean);
		
		property.addPropertyChangeListener(listener);
	}
	@Test
	public void testPropertyChangeListenerOnProperty_whenRemoveIt_thenListenerRemoved()
	{
		ObservableBean observableBean = new ObservableBean();
		addListener(observableBean);
		AbstractProperty<Boolean> property = createProperty(observableBean);
		
		property.removePropertyChangeListener(listener);
		
		assertHasNoListener(observableBean);
	}
	private void assertHasNoListener(ObservableBean observableBean)
	{
		Assert.assertFalse(observableBean.hasPropertyChangeListener(Bean.PROPERTY, listener));
	}
	private void addListener(ObservableBean observableBean)
	{
		observableBean.addPropertyChangeListener(Bean.PROPERTY, listener);
	}
	@Test
	public void testGivenObservableBean_whenRemoveNonExistingPropertyChangeListener_thenOperationIgnored()
	{
		ObservableBean observableBean = new ObservableBean();
		AbstractProperty<Boolean> property = createProperty(observableBean);
		
		property.removePropertyChangeListener(listener);
	}
	@Test
	public void testGivenNonObservableBean_whenRemovePropertyChangeListener_thenOperationIgnored()
	{
		Bean bean = new Bean();
		AbstractProperty<Boolean> property = createProperty(bean);
		
		property.removePropertyChangeListener(listener);
	}
	private AbstractProperty<Boolean> createProperty(Object bean)
	{
		PropertyAccessor<Boolean> propertyAccessor = PropertyAccessorUtils.createPropertyAccessor(bean.getClass(), "property");
		return new PropertyImpl<Boolean>(bean, propertyAccessor);
	}
	public static class PropertyImpl<T> extends AbstractProperty<T>
	{
		public PropertyImpl(Object bean, PropertyAccessor<T> propertyAccessor)
		{
			super(bean, propertyAccessor);
		}
	}
}
