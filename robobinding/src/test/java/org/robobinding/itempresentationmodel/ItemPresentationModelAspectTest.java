package org.robobinding.itempresentationmodel;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.aspects.PresentationModelMixin;
import org.robobinding.aspects.PropertyChangeListenerTester;
import org.robobinding.property.ObservableBean;

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

    private void observePropertyChange(ObservableBean itemPresentationModel) {
	itemPresentationModel.addPropertyChangeListener(ItemPresentationModel_AutoCodeGeneration.PROPERTY, propertyChangeListenerTester);
    }

    private void updateData(ItemPresentationModel<Object> itemPresentationModel) {
	itemPresentationModel.updateData(0, new Object());
    }

    @SuppressWarnings("rawtypes")
    @DataPoints
    public static ItemPresentationModel[] manualPresentationModelImplementations = {new ItemPresentationModel_ManualImplementation1(),
	    new ItemPresentationModel_ManualImplementation2()};

    @Theory
    public void whenImplementsPrensentationModelManually_thenNoAutoCodeGenerationTriggered(
	    @SuppressWarnings("rawtypes") ItemPresentationModel manualItemPresentationModelImplementation) {
	Assert.assertThat(manualItemPresentationModelImplementation, CoreMatchers.not(CoreMatchers.instanceOf(PresentationModelMixin.class)));
    }
}
