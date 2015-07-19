package org.robobinding.viewattribute;

import static org.robobinding.viewattribute.MockBindingContextBuilder.aBindingContext;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.ValueModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockPresentationModelAdapterBuilder {
	
	public static <PropertyType> PresentationModelAdapter aPresentationModelAdapterWithReadOnlyProperty(String propertyName,
			ValueModel<PropertyType> propertyValueModel) {
		return aBindingContext().withReadOnlyProperty(propertyName, propertyValueModel).build();
	}


	public static <PropertyType> PresentationModelAdapter aPresentationModelAdapterWithProperty(String propertyName, 
			ValueModel<PropertyType> propertyValueModel) {
		return aBindingContext().withProperty(propertyName, propertyValueModel).build();
	}

}
