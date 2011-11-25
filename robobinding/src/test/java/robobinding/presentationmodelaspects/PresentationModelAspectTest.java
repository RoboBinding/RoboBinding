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
package robobinding.presentationmodelaspects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import robobinding.internal.org_apache_commons_lang3.reflect.MethodUtils;
import robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class PresentationModelAspectTest
{
	private PropertyChangeListenerTester propertyChangeListenerTester;
	@Before
	public void setUp()
	{
		propertyChangeListenerTester = new PropertyChangeListenerTester();
	}
	
	@DataPoints
	public static AbstractPresentationModel[] differentPresentationModels = {
		new NotImplementsObservablePresentationModel(),
		new ImplementsObservablePresentationModel()};
	@Theory
	public void givenObservePropertyChangeOnPresentationModel_whenFirePropertyChange_thenListenerGetNotified(AbstractPresentationModel presentationModel)
	{
		observePropertyChange((ObservablePresentationModel)presentationModel);
		
		firePropertyChangeOn((ObservablePresentationModel)presentationModel);
		
		propertyChangeListenerTester.assertPropertyChangedOnce();
	}
	
	private void observePropertyChange(ObservablePresentationModel presentationModel)
	{
		presentationModel.addPropertyChangeListener(AbstractPresentationModel.PROPERTY, propertyChangeListenerTester);
	}
	
	private void firePropertyChangeOn(ObservablePresentationModel observablePresentationModel)
	{
		PresentationModelChangeSupport presentationModelChangeSupport = observablePresentationModel.getPresentationModelChangeSupport();
		presentationModelChangeSupport.firePropertyChange(AbstractPresentationModel.PROPERTY);
	}
	
	@DataPoints
	public static SetterAndPropertyChangeExpectation[] setterAndPropertyChangeExpectations = {
		SetterAndPropertyChangeExpectation.PROPERTY,
		SetterAndPropertyChangeExpectation.CUSTOM_PROPERTY,
		SetterAndPropertyChangeExpectation.PROPERTY_WITH_RETURN_VALUE,
		SetterAndPropertyChangeExpectation.PROPERTY_WITH_MULTIPLE_PARAMETERS};
	@Theory
	public void givenObservePropertyChangeOnPresentationModel_whenSetProperty_thenMatchesPropertyChangeExpectation(SetterAndPropertyChangeExpectation setterAndPropertyChangeExpectation)
	{
		PresentationModel presentationModel = new PresentationModel();
		observePropertyChange(presentationModel);
		
		setterAndPropertyChangeExpectation.setProperty(presentationModel);
		
		setterAndPropertyChangeExpectation.assertExpectation(propertyChangeListenerTester);
	}
	
	@DataPoints
	public static PresentationModelRefreshMethod[] presentationModelRefreshMethods = {
		new PresentationModelRefreshMethod.CustomPresentationModelRefreshMethod(),
		new PresentationModelRefreshMethod.DefaultPresentationModelRefreshMethod()};
	@Theory
	public void whenInvokePresentationModelRefreshMethod_thenListenerGetNotified(PresentationModelRefreshMethod presentationModelRefreshMethod)
	{
		PresentationModelWithRefreshMethod presentationModelWithRefreshMethod = new PresentationModelWithRefreshMethod();
		presentationModelRefreshMethod.setPresentationModelWithRefreshMethod(presentationModelWithRefreshMethod);
		observePropertyChange(presentationModelWithRefreshMethod);
		
		presentationModelRefreshMethod.invoke();
		
		propertyChangeListenerTester.assertPropertyChangedOnce();
	}
	
	@Test
	public void shouldContainDefaultPresentationModelRefreshMethod()
	{
		Assert.assertNotNull(MethodUtils.getAccessibleMethod(PresentationModelWithRefreshMethod.class, "refresh", new Class<?>[0]));
	}
}
