package org.robobinding.presentationmodel;

import org.robobinding.function.Function;
import org.robobinding.function.Functions;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.Properties;
import org.robobinding.property.ValueModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class PresentationModelAdapterImpl implements PresentationModelAdapter {
	private final Functions functions;
	private final Properties properties;
	private final Class<?> presentationModelClass;

	public PresentationModelAdapterImpl(Properties properties, Functions functions, Class<?> presentationModelClass) {
		this.properties = properties;
		this.functions = functions;
		this.presentationModelClass = presentationModelClass;
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
