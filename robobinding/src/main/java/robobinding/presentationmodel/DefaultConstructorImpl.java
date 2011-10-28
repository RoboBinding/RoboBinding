/**
 * DefaultConstructorImpl.java
 * 17 Oct 2011 Copyright Cheng Wei and Robert Taylor
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import robobinding.utils.Validate;

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
		Validate.notNull(itemPresentationModelConstructor, "itemPresentationModelClass does not have a default constructor");
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