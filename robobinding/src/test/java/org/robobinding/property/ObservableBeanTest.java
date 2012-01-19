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
package org.robobinding.property;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;




/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ObservableBeanTest
{
	private static final String PROPERTY_NAME = "property";
	
	private ObservableBean observableBean;
	private PresentationModelPropertyChangeListener listener;
	@Before
	public void setUp()
	{
		listener = new MockPresentationModelPropertyChangeListener();
	}
	@Test
	public void testGivenObservableTestBean_whenAddPropertyChangeListener_thenSuccessful()
	{
		ObservableProperties testBean = mock(ObservableProperties.class);
		createObservableBean(testBean);
		
		addPropertyChangeListener();
	}
	
	@Test(expected=RuntimeException.class)
	public void givenNonObservableTestBean_whenAddPropertyChangeListener_thenThrowException()
	{
		Object testBean = mock(Object.class);
		createObservableBean(testBean);
		
		addPropertyChangeListener();
	}
	private void addPropertyChangeListener()
	{
		observableBean.addPropertyChangeListener(PROPERTY_NAME, listener);
	}
	@Test
	public void givenObservableTestBean_whenRemovePropertyChangeListener_thenSuccessful()
	{
		ObservableProperties testBean = mock(ObservableProperties.class);
		createObservableBean(testBean);
		
		removePropertyChangeListener();
	}
	@Test
	public void testGivenNonObservableBean_whenRemovePropertyChangeListener_thenOperationIgnored()
	{
		Object testBean = mock(Object.class);
		createObservableBean(testBean);
		
		removePropertyChangeListener();
	}
	private void removePropertyChangeListener()
	{
		observableBean.removePropertyChangeListener(PROPERTY_NAME, listener);
	}
	private void createObservableBean(Object bean)
	{
		observableBean = new ObservableBean(bean);
	}
}
