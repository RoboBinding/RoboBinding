package org.robobinding.test;

import static org.robobinding.internal.guava.Preconditions.checkNotNull;
import static org.robobinding.util.Preconditions.checkNotBlank;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapterFactory;
import org.robobinding.property.ValueModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PresentationModelTester {
    private final PresentationModelAdapter presentationModelAdapter;

    PresentationModelTester(PresentationModelAdapter presentationModelAdapter) {
	this.presentationModelAdapter = presentationModelAdapter;
    }

    private PresentationModelPropertyChangeSpy spyPropertyChange(String propertyName) {
	ValueModel<Object> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyName);
	PresentationModelPropertyChangeSpy spy = new PresentationModelPropertyChangeSpy();
	valueModel.addPropertyChangeListener(spy);
	return spy;
    }

    public static PresentationModelPropertyChangeSpy spyPropertyChange(Object presentationModel, String propertyName) {
	checkNotNull(presentationModel, "presentationModel must not be null");
	checkNotBlank(propertyName, "propertyName must not be empty");
	PresentationModelAdapter presentationModelAdapter = new PresentationModelAdapterFactory().create(presentationModel);
	PresentationModelTester presentationModelTester = new PresentationModelTester(presentationModelAdapter);
	return presentationModelTester.spyPropertyChange(propertyName);
    }
}
