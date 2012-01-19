/**
 * PropertyChangeSupportTest.java
 * Nov 13, 2011 Copyright Cheng Wei and Robert Taylor
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.PresentationModelPropertyChangeListener;
import org.robobinding.property.PresentationModelPropertyChangeSupport;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PresentationModelPropertyChangeSupportTest
{
	private Bean bean;
	private PresentationModelPropertyChangeSupport propertyChangeSupport;
	@Before
	public void setUp()
	{
		bean = new Bean();
		propertyChangeSupport = new PresentationModelPropertyChangeSupport(bean);
	}
	@Test(expected=IllegalArgumentException.class)
	public void whenAddListenerToNonExistingProperty_thenThrowException()
	{
		createListenerOnProperty("nonExistingProperty");
	}
	@Test
	public void givenListenerOnProperty1_whenRemoveIt_thenShouldNotReceiveNotification()
	{
		MockPresentationModelPropertyChangeListener mockListener = createListenerOnProperty(Bean.PROPERTY1);
		
		propertyChangeSupport.removePropertyChangeListener(Bean.PROPERTY1, mockListener);
		
		propertyChangeSupport.firePropertyChange(Bean.PROPERTY1);
		assertFalse(mockListener.propertyChangedFired);
	}
	private MockPresentationModelPropertyChangeListener createListenerOnProperty(String propertyName)
	{
		MockPresentationModelPropertyChangeListener mockListener = new MockPresentationModelPropertyChangeListener();
		propertyChangeSupport.addPropertyChangeListener(propertyName, mockListener);
		return mockListener;
	}
	@Test
	public void givenListenerOnProperty1_whenFirePropertyChange_thenShouldReceiveNotification()
	{
		MockPresentationModelPropertyChangeListener mockListener = createShouldBeNotifiedListenerOnProperty(Bean.PROPERTY1);
		
		propertyChangeSupport.firePropertyChange(Bean.PROPERTY1);
		
		assertTrue(mockListener.propertyChangedFired);
	}
	@Test
	public void givenListenersOnProperty1AndProperty2_whenFireChangeAll_thenShouldAllReceiveNotifications()
	{
		PresentationModelPropertyChangeListener listenerOnProperty1 = createShouldBeNotifiedListenerOnProperty(Bean.PROPERTY1);
		PresentationModelPropertyChangeListener listenerOnProperty2 = createShouldBeNotifiedListenerOnProperty(Bean.PROPERTY2);
		
		propertyChangeSupport.fireChangeAll();
		
		EasyMock.verify(listenerOnProperty1, listenerOnProperty2);
	}
	private MockPresentationModelPropertyChangeListener createShouldBeNotifiedListenerOnProperty(String propertyName)
	{
		MockPresentationModelPropertyChangeListener mockPropertyChangeListener = new MockPresentationModelPropertyChangeListener();
		
		propertyChangeSupport.addPropertyChangeListener(propertyName, mockPropertyChangeListener);
		return mockPropertyChangeListener;
	}
	public static class Bean
	{
		public static final String PROPERTY1 = "property1";
		public static final String PROPERTY2 = "property2";
		
		public boolean getProperty1()
		{
			return true;
		}
		public String getProperty2()
		{
			return null;
		}
	}
}
