/**
 * RowsValueModel.java
 * Sep 27, 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.sample.contact;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import robobinding.utils.Validate;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public abstract class AbstractCollectionValueModel<R extends RowObservableBean<B>, B>
{
	private final RowPresentationModelFactory<R, B> factory;
	protected AbstractCollectionValueModel(Class<R> rowPresentationModelClass)
	{
		this(new DefaultRowPresentationModelFactory<R,B>(rowPresentationModelClass));
	}
	protected AbstractCollectionValueModel(RowPresentationModelFactory<R, B> factory)
	{
		Validate.notNull(factory, "Factory must not be null");
		this.factory = factory;
	}
	public final R newRowPresentationModel()
	{
		return factory.newRowPresentationModel();
	}
	public final void fillData(R rowPresentationModel, B bean)
	{
		rowPresentationModel.setData(bean);
	}

	private static class DefaultRowPresentationModelFactory<R extends RowObservableBean<B>, B> implements RowPresentationModelFactory<R, B>
	{
		private Constructor<R> rowPresentationModelConstructor;
		public DefaultRowPresentationModelFactory(Class<R> rowPresentationModelClass)
		{
			Validate.notNull(rowPresentationModelClass, "rowPresentationModelClass must not be null");
			rowPresentationModelConstructor = ConstructorUtils.getAccessibleConstructor(rowPresentationModelClass, new Class<?>[0]);
			Validate.notNull(rowPresentationModelConstructor, "rowPresentationModelClass does not have a sdefault constructor");
		}
		@Override
		public R newRowPresentationModel()
		{
			try
			{
				return rowPresentationModelConstructor.newInstance(new Object[0]);
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
}
