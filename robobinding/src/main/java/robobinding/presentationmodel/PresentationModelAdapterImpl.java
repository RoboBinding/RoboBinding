/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package robobinding.presentationmodel;

import robobinding.function.CachedFunctions;
import robobinding.function.Function;
import robobinding.internal.org_apache_commons_lang3.Validate;
import robobinding.property.AbstractDataSetProperty;
import robobinding.property.CachedProperties;
import robobinding.property.Properties;
import robobinding.property.PropertyValueModel;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class PresentationModelAdapterImpl implements PresentationModelAdapter
{
	private final CachedFunctions functions;
	private final Properties properties;

	public PresentationModelAdapterImpl(Object presentationModel)
	{
		Validate.notNull(presentationModel);
		
		properties = new CachedProperties(presentationModel);
		functions = new CachedFunctions(presentationModel);
	}
	public Class<?> getPropertyType(String propertyName)
	{
		return properties.getPropertyType(propertyName);
	}
	public <T> PropertyValueModel<T> getPropertyValueModel(String propertyName)
	{
		return properties.getReadWriteProperty(propertyName);
	}
	public <T> PropertyValueModel<T> getReadOnlyPropertyValueModel(String propertyName)
	{
		return properties.getReadOnlyProperty(propertyName);
	}
	@Override
	public AbstractDataSetProperty<?> getDataSetPropertyValueModel(String propertyName)
	{
		return properties.getReadOnlyDataSetProperty(propertyName);
	}
	@Override
	public Function findFunction(String functionName, Class<?>... parameterTypes)
	{
		return functions.find(functionName, parameterTypes);
	}
}
