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
package org.robobinding.itempresentationmodel;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.presentationmodel.PresentationModelMixin;
import org.robobinding.presentationmodel.PropertyChangeListenerTester;
import org.robobinding.property.ObservableProperties;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class ItemPresentationModelAspectTest {
    private PropertyChangeListenerTester propertyChangeListenerTester;

    @Before
    public void setUp() {
	propertyChangeListenerTester = new PropertyChangeListenerTester();
    }

    @Test
    public void givenObservePropertyChangeOnItemPresentationModel_whenSetData_thenListenerGetNotified() {
	ItemPresentationModel_AutoCodeGeneration itemPresentationModel = new ItemPresentationModel_AutoCodeGeneration();
	observePropertyChange(itemPresentationModel);

	updateData(itemPresentationModel);

	propertyChangeListenerTester.assertPropertyChangedOnce();
    }

    private void observePropertyChange(ObservableProperties itemPresentationModel) {
	itemPresentationModel.addPropertyChangeListener(ItemPresentationModel_AutoCodeGeneration.PROPERTY, propertyChangeListenerTester);
    }

    private void updateData(ItemPresentationModel<Object> itemPresentationModel) {
	itemPresentationModel.updateData(0, new Object());
    }

    @SuppressWarnings("rawtypes")
    @DataPoints
    public static ItemPresentationModel[] manualPresentationModelImplementations = { new ItemPresentationModel_ManualImplementation1(),
	    new ItemPresentationModel_ManualImplementation2() };

    @Theory
    public void whenImplementsPrensentationModelManually_thenNoAutoCodeGenerationTriggered(
	    @SuppressWarnings("rawtypes") ItemPresentationModel manualItemPresentationModelImplementation) {
	Assert.assertThat(manualItemPresentationModelImplementation, CoreMatchers.not(CoreMatchers.instanceOf(PresentationModelMixin.class)));
    }
}
