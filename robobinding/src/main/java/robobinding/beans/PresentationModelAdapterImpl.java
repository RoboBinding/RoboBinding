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
package robobinding.beans;

import java.lang.reflect.Method;
import java.util.Map;

import robobinding.presentationmodel.AbstractDataSetValueModel;
import robobinding.presentationmodel.CustomPropertyProvider;
import robobinding.presentationmodel.DependentPropertyValueModelProvider;
import robobinding.utils.Validate;
import robobinding.value.ValueModel;

import com.google.common.collect.Maps;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class PresentationModelAdapterImpl implements PresentationModelAdapter, DependentPropertyValueModelProvider
{
	private final Object presentationModel;

	private final Map<String, PropertyAdapter<?>> propertyAdapterMappings;

	public PresentationModelAdapterImpl(Object presentationModel)
	{
		Validate.notNull(presentationModel);
		
		this.presentationModel = presentationModel;
		
		propertyAdapterMappings = Maps.newHashMap();
	}
	public Class<?> getPropertyType(String propertyName)
	{
		PropertyAdapter<Object> propertyAdapter = getPropertyAdapter(propertyName, false);
		return propertyAdapter.getPropertyType();
	}
	@Override
	public <T> ValueModel<T> getReadOnlyPropertyValueModel(String propertyName)
	{
		return getPropertyAdapter(propertyName, false);
	}
	
	public <T> ValueModel<T> getPropertyValueModel(String propertyName)
	{
		return getPropertyAdapter(propertyName, true);
	}
	private <T> PropertyAdapter<T> getPropertyAdapter(String propertyName, boolean isReadWriteProperty)
	{
		Validate.notBlank(propertyName, "The property name must not be empty");
		
		@SuppressWarnings("unchecked")
		PropertyAdapter<T> propertyAdapter = (PropertyAdapter<T>)propertyAdapterMappings.get(propertyName);
		if (propertyAdapter == null)
		{
			propertyAdapter = createPropertyAdapter(propertyName, isReadWriteProperty);
			propertyAdapterMappings.put(propertyName, propertyAdapter);
		}else
		{
			propertyAdapter.checkReadWriteProperty(isReadWriteProperty);
		}
		return propertyAdapter;
	}
	
	private <T> PropertyAdapter<T> createPropertyAdapter(String propertyName, boolean isReadWriteProperty)
	{
		if(presentationModel instanceof CustomPropertyProvider)
		{
			CustomPropertyProvider customPropertyProvider = (CustomPropertyProvider)presentationModel;
			@SuppressWarnings("unchecked")
			CustomPropertyDescriptor<T> customPropertyDescriptor = (CustomPropertyDescriptor<T>)customPropertyProvider.createCustomProperty(propertyName, this);
			if(customPropertyDescriptor != null)
			{
				return new CustomPropertyAdapter<T>(presentationModel, propertyName, customPropertyDescriptor);
			}
		}
		return new RegularPropertyAdapter<T>(presentationModel, propertyName, isReadWriteProperty);
	}
	
	public Object getPresentationModel()
	{
		return presentationModel;
	}
	
	@Override
	public <T> ValueModel<T> getValueModel(String dependentPropertyName)
	{
		return getPropertyAdapter(dependentPropertyName, false);
	}
	@Override
	public AbstractDataSetValueModel getDataSetPropertyValueModel(String propertyName)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	private Method getPreferredMethod()
	{
		return findMethodWithMatchingName();
	}

	private Method getNoArgsMethod()
	{
		return findMethodWithMatchingName();
	}
	
	private Method findMethodWithMatchingName(Class<?>... parameterTypes)
	{
		try
		{
			return presentationModel.getClass().getMethod(commandName, parameterTypes);
		} 
		catch (NoSuchMethodException e)
		{}
		
		return null;
	}
}
