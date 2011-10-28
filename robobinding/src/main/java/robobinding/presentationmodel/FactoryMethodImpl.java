/**
 * FactoryMethodImpl.java
 * Oct 28, 2011 Copyright Cheng Wei and Robert Taylor
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.reflect.MethodUtils;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class FactoryMethodImpl<T> implements ItemPresentationModelFactory<T>
{
	private Object object;
	private Class<? extends ItemPresentationModel<T>> itemPresentationModelClass;
	private Method factoryMethod;
	public FactoryMethodImpl(Object object, Class<? extends ItemPresentationModel<T>> itemPresentationModelClass, String methodName)
	{
		this.object = object;
		this.itemPresentationModelClass = itemPresentationModelClass;
		initializeFactoryMethod(methodName);
	}
	private void initializeFactoryMethod(String methodName)
	{
		factoryMethod = MethodUtils.getAccessibleMethod(object.getClass(), methodName, new Class<?>[0]);
		if(factoryMethod == null)
		{
			throw new RuntimeException("FactoryMethod '"+describeExpectedFactoryMethod(methodName)+"' does not exist");
		}else
		{
			validateReturnType(methodName);
		}
	}
	private String describeExpectedFactoryMethod(String methodName)
	{
		String className = object.getClass().getName();
		String itemPresentationModelClassName = itemPresentationModelClass.getName();
		return "public "+itemPresentationModelClassName+"[or subclass] "+className+"."+methodName+"()";
	}
	private void validateReturnType(String methodName)
	{
		Class<?> returnType = factoryMethod.getReturnType();
		if(!itemPresentationModelClass.isAssignableFrom(returnType))
		{
			String returnTypeName = returnType.getName();
			throw new RuntimeException("Expected FactoryMethod '"+describeExpectedFactoryMethod(methodName)+"' but has invalid actual returnType '"+returnTypeName+"'");
		}
	}
	@Override
	public ItemPresentationModel<T> newItemPresentationModel()
	{
		try
		{
			@SuppressWarnings("unchecked")
			ItemPresentationModel<T> itemPresentationModel = (ItemPresentationModel<T>)factoryMethod.invoke(object, new Object[0]);
			return itemPresentationModel;
		} catch (IllegalArgumentException e)
		{
			throw new RuntimeException(e);
		} catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		} catch (InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}
	
}
