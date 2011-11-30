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

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import robobinding.property.ObservableProperties;

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
	public static SetterAndPropertyChangeExpectation[] setterAndPropertyChangeExpectations = {
		SetterAndPropertyChangeExpectation.PROPERTY,
		SetterAndPropertyChangeExpectation.CUSTOM_PROPERTY,
		SetterAndPropertyChangeExpectation.PROPERTY_WITH_RETURN_VALUE,
		SetterAndPropertyChangeExpectation.PROPERTY_WITH_MULTIPLE_PARAMETERS};
	@Theory
	public void givenObservePropertyChangeOnPresentationModel_whenSetProperty_thenMatchesPropertyChangeExpectation(SetterAndPropertyChangeExpectation setterAndPropertyChangeExpectation)
	{
		PresentationModel_AutoCodeGeneration presentationModel = new PresentationModel_AutoCodeGeneration();
		observePropertyChange(presentationModel);
		
		setterAndPropertyChangeExpectation.setProperty(presentationModel);
		
		setterAndPropertyChangeExpectation.assertExpectation(propertyChangeListenerTester);
	}
	
	@Test
	public void whenInvokeDefaultRefreshMethod_thenListenerGetNotified()
	{
		PresentationModel_AutoCodeGeneration presentationModel = new PresentationModel_AutoCodeGeneration();
		observePropertyChange(presentationModel);
		
		presentationModel.refreshPresentationModel();
		
		propertyChangeListenerTester.assertPropertyChangedOnce();
	}
	private void observePropertyChange(ObservableProperties presentationModel)
	{
		presentationModel.addPropertyChangeListener(PresentationModel_AutoCodeGeneration.PROPERTY, propertyChangeListenerTester);
	}
	
	@DataPoints
	public static ObservableProperties[] manualPresentationModelImplementations = {
		new PresentationModel_ManualImplementation1(),
		new PresentationModel_ManualImplementation2()};
	@Theory
	public void whenImplementsPrensentationModelManually_thenNoAutoCodeGenerationTriggered(ObservableProperties manualPresentationModelImplementation)
	{
		Assert.assertThat(manualPresentationModelImplementation, CoreMatchers.not(CoreMatchers.instanceOf(PresentationModelMixin.class)));
	}
}
