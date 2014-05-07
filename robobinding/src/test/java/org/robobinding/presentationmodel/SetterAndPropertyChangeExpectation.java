package org.robobinding.presentationmodel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class SetterAndPropertyChangeExpectation {
    public abstract void setProperty(PresentationModel_AutoCodeGeneration presentationModel);

    public void assertExpectation(PropertyChangeListenerTester propertyChangeListenerTester) {
	propertyChangeListenerTester.assertTimesOfPropertyChanged(getExpectedTimesOfPropertyChanged());
    }

    protected int getExpectedTimesOfPropertyChanged() {
	return 0;
    }

    public static final SetterAndPropertyChangeExpectation PROPERTY = new SetterAndPropertyChangeExpectation() {

	@Override
	public void setProperty(PresentationModel_AutoCodeGeneration presentationModel) {
	    presentationModel.setProperty(true);
	}

	@Override
	protected int getExpectedTimesOfPropertyChanged() {
	    return 1;
	}
    };

    public static final SetterAndPropertyChangeExpectation CUSTOM_PROPERTY = new SetterAndPropertyChangeExpectation() {

	@Override
	public void setProperty(PresentationModel_AutoCodeGeneration presentationModel) {
	    presentationModel.setCustomProperty(true);
	}
    };

    public static final SetterAndPropertyChangeExpectation PROPERTY_WITH_RETURN_VALUE = new SetterAndPropertyChangeExpectation() {

	@Override
	public void setProperty(PresentationModel_AutoCodeGeneration presentationModel) {
	    presentationModel.setPropertyWithReturnValue(true);
	}
    };

    public static final SetterAndPropertyChangeExpectation PROPERTY_WITH_MULTIPLE_PARAMETERS = new SetterAndPropertyChangeExpectation() {

	@Override
	public void setProperty(PresentationModel_AutoCodeGeneration presentationModel) {
	    presentationModel.setPropertyWithMultipleParameters(true, "");

	}
    };
}
