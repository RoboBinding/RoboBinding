/**
 * CustomPropertyDescriptor.java
 * Oct 14, 2011 Copyright Cheng Wei and Robert Taylor
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

import java.lang.reflect.ParameterizedType;

import robobinding.utils.Validate;
import robobinding.value.ValueModel;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public class CustomPropertyDescriptor<T>
{
	private ValueModel<T> valueModel;
	private boolean isReadWriteProperty;
	private Class<T> propertyType;
	private CustomPropertyDescriptor(ValueModel<T> valueModel, boolean isReadWriteProperty)
	{
		Validate.notNull(valueModel);
		
		this.valueModel = valueModel;
		this.isReadWriteProperty = isReadWriteProperty;
		initializePropertyType();
	}
	
	@SuppressWarnings("unchecked")
	private void initializePropertyType()
	{
    	Class<?> klass = getClass();
    	ParameterizedType genericSuperclass = (ParameterizedType)klass.getGenericSuperclass();
	    propertyType =(Class<T>)genericSuperclass.getActualTypeArguments()[0];
	}
	
	ValueModel<T> getPropertyValueModel()
	{
		return valueModel;
	}

	Class<?> getPropertyType()
	{
		return propertyType;
	}

	boolean isReadable()
	{
		return true;
	}

	boolean isWritable()
	{
		return isReadWriteProperty;
	}
	
	public static <T> CustomPropertyDescriptor<T> createReadWritePropertyDescriptor(ValueModel<T> valueModel)
	{
		return new CustomPropertyDescriptor<T>(valueModel, true);
	}
	public static <T> CustomPropertyDescriptor<T> createReadOnlyPropertyDescriptor(ValueModel<T> valueModel)
	{
		return new CustomPropertyDescriptor<T>(valueModel, false);
	}
}
