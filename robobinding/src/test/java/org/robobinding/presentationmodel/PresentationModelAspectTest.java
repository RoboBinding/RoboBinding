package org.robobinding.presentationmodel;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.property.ObservableBean;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class PresentationModelAspectTest {
    private PropertyChangeListenerTester propertyChangeListenerTester;

    @Before
    public void setUp() {
	propertyChangeListenerTester = new PropertyChangeListenerTester();
    }

    @DataPoints
    public static SetterAndPropertyChangeExpectation[] setterAndPropertyChangeExpectations = {SetterAndPropertyChangeExpectation.PROPERTY,
	    SetterAndPropertyChangeExpectation.CUSTOM_PROPERTY, SetterAndPropertyChangeExpectation.PROPERTY_WITH_RETURN_VALUE,
	    SetterAndPropertyChangeExpectation.PROPERTY_WITH_MULTIPLE_PARAMETERS};

    @Theory
    public void givenObservePropertyChangeOnPresentationModel_whenSetProperty_thenMatchesPropertyChangeExpectation(
	    SetterAndPropertyChangeExpectation setterAndPropertyChangeExpectation) {
	PresentationModel_AutoCodeGeneration presentationModel = new PresentationModel_AutoCodeGeneration();
	observePropertyChange(presentationModel);

	setterAndPropertyChangeExpectation.setProperty(presentationModel);

	setterAndPropertyChangeExpectation.assertExpectation(propertyChangeListenerTester);
    }

    @Test
    public void whenInvokeDefaultRefreshMethod_thenListenerGetNotified() {
	PresentationModel_AutoCodeGeneration presentationModel = new PresentationModel_AutoCodeGeneration();
	observePropertyChange(presentationModel);

	presentationModel.refreshPresentationModel();

	propertyChangeListenerTester.assertPropertyChangedOnce();
    }

    private void observePropertyChange(ObservableBean presentationModel) {
	presentationModel.addPropertyChangeListener(PresentationModel_AutoCodeGeneration.PROPERTY, propertyChangeListenerTester);
    }

    @DataPoints
    public static ObservableBean[] manualPresentationModelImplementations = {new PresentationModel_ManualImplementation1(),
	    new PresentationModel_ManualImplementation2()};

    @Theory
    public void whenImplementsPrensentationModelManually_thenNoAutoCodeGenerationTriggered(
	    ObservableBean manualPresentationModelImplementation) {
	Assert.assertThat(manualPresentationModelImplementation, not(instanceOf(PresentationModelMixin.class)));
    }
}
