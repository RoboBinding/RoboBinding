package org.robobinding.presentationmodel;

import org.robobinding.function.Function;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.ValueModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public interface PresentationModelAdapter {
	Class<?> getPropertyType(String propertyName);

	<T> ValueModel<T> getReadOnlyPropertyValueModel(String propertyName);

	<T> ValueModel<T> getPropertyValueModel(String propertyName);

	DataSetValueModel getDataSetPropertyValueModel(String propertyName);

	Function findFunction(String functionName, Class<?>... parameterTypes);

	String getPresentationModelClassName();

	Object getSubPresentationModelProperty(String propertyName);
}
