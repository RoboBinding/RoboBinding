package org.robobinding.presentationmodel;

import static com.google.common.base.Preconditions.checkNotNull;

import org.robobinding.function.CachedFunctions;
import org.robobinding.function.Function;
import org.robobinding.property.CachedProperties;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.Properties;
import org.robobinding.property.ValueModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PresentationModelAdapterImpl implements PresentationModelAdapter {
    private final CachedFunctions functions;
    private final Properties properties;
    private final Class<?> presentationModelClass;

    public PresentationModelAdapterImpl(Object presentationModel) {
	checkNotNull(presentationModel, "presentationModel must not be null");

	properties = CachedProperties.create(presentationModel);
	functions = new CachedFunctions(presentationModel);
	presentationModelClass = presentationModel.getClass();
    }

    public Class<?> getPropertyType(String propertyName) {
	return properties.getPropertyType(propertyName);
    }

    public <T> ValueModel<T> getPropertyValueModel(String propertyName) {
	return properties.getReadWriteProperty(propertyName);
    }

    public <T> ValueModel<T> getReadOnlyPropertyValueModel(String propertyName) {
	return properties.getReadOnlyProperty(propertyName);
    }

    @Override
    public DataSetValueModel<?> getDataSetPropertyValueModel(String propertyName) {
	return properties.getDataSetProperty(propertyName);
    }

    @Override
    public Function findFunction(String functionName, Class<?>... parameterTypes) {
	return functions.find(functionName, parameterTypes);
    }

    @Override
    public String getPresentationModelClassName() {
	return presentationModelClass.getName();
    }
}
