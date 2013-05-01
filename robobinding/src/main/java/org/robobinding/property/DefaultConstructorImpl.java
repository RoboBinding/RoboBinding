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
package org.robobinding.property;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;
import org.robobinding.util.ConstructorUtils;



/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
final class DefaultConstructorImpl<T> implements ItemPresentationModelFactory<T>
{
	private Constructor<? extends ItemPresentationModel<T>> itemPresentationModelConstructor;
	public DefaultConstructorImpl(Class<? extends ItemPresentationModel<T>> itemPresentationModelClass)
	{
		itemPresentationModelConstructor = ConstructorUtils.getAccessibleConstructor(itemPresentationModelClass, new Class<?>[0]);
		String className = itemPresentationModelClass.getName();
		checkNotNull(itemPresentationModelConstructor, "itemPresentationModelClass '"+className+"' does not have an accessible default constructor");
	}
	@Override
	public ItemPresentationModel<T> newItemPresentationModel()
	{
		try
		{
			return itemPresentationModelConstructor.newInstance(new Object[0]);
		} catch (IllegalArgumentException e)
		{
			throw new RuntimeException(e);
		} catch (InstantiationException e)
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