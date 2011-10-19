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
package robobinding.presentationmodel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import robobinding.utils.Validate;
import robobinding.value.ValueModel;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public abstract class AbstractDataSetValueModel<DataSetType, ItemType> implements ValueModel<DataSetType>
{
	private final ItemPresentationModelFactory<ItemType> factory;

	protected AbstractDataSetValueModel(ItemPresentationModelFactory<ItemType> factory)
	{
		Validate.notNull(factory, "Factory must not be null");
		this.factory = factory;
	}
	public final ItemPresentationModel<ItemType> newItemPresentationModel()
	{
		return factory.newItemPresentationModel();
	}
	
	public abstract int size();
	public abstract ItemType getItem(int position);
	
	public void updateItemPresentationModel(ItemPresentationModel<ItemType> itemPresentationModel, int position)
	{
		ItemType item = getItem(position);
		itemPresentationModel.setData(position, item);
	}
	protected final static class DefaultItemPresentationModelFactory<ItemType> implements ItemPresentationModelFactory<ItemType>
	{
		private Constructor<? extends ItemPresentationModel<ItemType>> itemPresentationModelConstructor;
		public DefaultItemPresentationModelFactory(Class<? extends ItemPresentationModel<ItemType>> itemPresentationModelClass)
		{
			Validate.notNull(itemPresentationModelClass, "itemPresentationModelClass must not be null");
			itemPresentationModelConstructor = ConstructorUtils.getAccessibleConstructor(itemPresentationModelClass, new Class<?>[0]);
			Validate.notNull(itemPresentationModelConstructor, "itemPresentationModelClass does not have a sdefault constructor");
		}
		@Override
		public ItemPresentationModel<ItemType> newItemPresentationModel()
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
	
}
