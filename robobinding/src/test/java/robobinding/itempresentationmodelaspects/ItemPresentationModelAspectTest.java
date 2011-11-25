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
package robobinding.itempresentationmodelaspects;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import robobinding.itempresentationmodel.ItemPresentationModel;
import robobinding.presentationmodelaspects.ObservablePresentationModel;
import robobinding.presentationmodelaspects.PropertyChangeListenerTester;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class ItemPresentationModelAspectTest
{
	@DataPoints
	public static AbstractItemPresentationModel[] differentItemPresentationModels = {
		new NotImplementsObservablePresentationModel(),
		new ImplementsObservablePresentationModel()}; 
	
	private PropertyChangeListenerTester propertyChangeListenerTester;
	@Before
	public void setUp()
	{
		propertyChangeListenerTester = new PropertyChangeListenerTester();
	}
	@Theory
	public void givenObservePropertyChangeOnItemPresentationModel_whenSetData_thenListenerGetNotified(AbstractItemPresentationModel itemPresentationModel)
	{
		observePropertyChange((ObservablePresentationModel)itemPresentationModel);
		
		setDataOn(itemPresentationModel);
		
		propertyChangeListenerTester.assertPropertyChangedOnce();
	}
	private void observePropertyChange(ObservablePresentationModel observablePresentationModel)
	{
		observablePresentationModel.addPropertyChangeListener(AbstractItemPresentationModel.PROPERTY, propertyChangeListenerTester);
	}
	private void setDataOn(ItemPresentationModel<Bean> itemPresentationModel)
	{
		itemPresentationModel.setData(0, new Bean());
	}
}
