package org.robobinding.test;

import static com.google.common.base.Preconditions.checkNotNull;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapterImpl;
import org.robobinding.property.ValueModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PresentationModelTester {
    private PresentationModelAdapter presentationModelAdapter;

    private PresentationModelTester(Object presentationModel) {
	checkNotNull(presentationModel, "presentationModel should not be null");
	presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
    }

    private PresentationModelPropertyChangeSpy spyPropertyChange(String propertyName) {
	ValueModel<Object> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyName);
	PresentationModelPropertyChangeSpy spy = new PresentationModelPropertyChangeSpy();
	valueModel.addPropertyChangeListener(spy);
	return spy;
    }

    public static PresentationModelPropertyChangeSpy spyPropertyChange(Object presentationModel, String propertyName) {
	PresentationModelTester presentationModelTester = new PresentationModelTester(presentationModel);
	return presentationModelTester.spyPropertyChange(propertyName);
    }
}
