/**
 * AbstractDataSetProperty.java
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
package robobinding.property;



import robobinding.itempresentationmodel.ItemPresentationModel;
import robobinding.itempresentationmodel.ItemPresentationModelFactory;
import robobinding.utils.StringUtils;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractDataSetProperty<T> extends AbstractProperty<Object>
{
	private ItemPresentationModelFactory<T> factory;

	protected AbstractDataSetProperty(Object bean, PropertyAccessor<Object> propertyAccessor)
	{
		super(bean, propertyAccessor);
		
		initializeFactory();
	}
	
	private void initializeFactory()
	{
		robobinding.ItemPresentationModel annotation = propertyAccessor.getAnnotation(robobinding.ItemPresentationModel.class);
		@SuppressWarnings("unchecked")
		Class<? extends ItemPresentationModel<T>> itemPresentationModelClass = (Class<? extends ItemPresentationModel<T>>)annotation.value();
		String factoryMethod = annotation.factoryMethod();
		if(StringUtils.isBlank(factoryMethod))
		{
			factory = new DefaultConstructorImpl<T>(itemPresentationModelClass);
		}else
		{
			factory = new FactoryMethodImpl<T>(bean, itemPresentationModelClass, factoryMethod);
		}
	}

	@Override
	public void setValue(Object newValue)
	{
		throw new UnsupportedOperationException();
	}
	public abstract int size();
	public abstract T getItem(int position);
	
	public ItemPresentationModel<T> newItemPresentationModel()
	{
		return factory.newItemPresentationModel();
	}
	public void updateItemPresentationModel(ItemPresentationModel<T> itemPresentationModel, int position)
	{
		T item = getItem(position);
		itemPresentationModel.setData(position, item);
	}
}
