package org.robobinding.presentationmodel;

import org.robobinding.function.Function;
import org.robobinding.function.Functions;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.Properties;
import org.robobinding.property.ValueModel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelAdapterImpl implements PresentationModelAdapter {
	private final Class<?> presentationModelClass;
	private final Properties properties;
	private final Functions functions;
	
	public PresentationModelAdapterImpl(Class<?> presentationModelClass, Properties properties, Functions functions) {
		this.presentationModelClass = presentationModelClass;
		this.properties = properties;
		this.functions = functions;
	}

	@Override
	public Class<?> getPropertyType(String propertyName) {
		return properties.getPropertyType(propertyName);
	}

	@Override
	public <T> ValueModel<T> getReadOnlyPropertyValueModel(String propertyName) {
		return properties.getReadOnlyProperty(propertyName);
	}

	@Override
	public <T> ValueModel<T> getPropertyValueModel(String propertyName) {
		return properties.getReadWriteProperty(propertyName);
	}

	@Override
	public DataSetValueModel getDataSetPropertyValueModel(String propertyName) {
		return properties.getDataSetProperty(propertyName);
	}
	
	@Override
	public Object getSubPresentationModelProperty(String propertyName) {
		ValueModel<Object> valueModel = properties.getReadOnlyProperty(propertyName);
		return valueModel.getValue();
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
