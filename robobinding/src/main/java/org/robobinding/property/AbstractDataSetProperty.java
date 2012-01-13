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



import org.robobinding.internal.org_apache_commons_lang3.StringUtils;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
abstract class AbstractDataSetProperty<T> extends AbstractProperty<Object> implements DataSetProperty<T>
{
	private ItemPresentationModelFactory<T> factory;
	private Object bean;
	private boolean isDataSetCached;
	private Object cachedDataSet;

	protected AbstractDataSetProperty(ObservableBean observableBean, PropertyAccessor<Object> propertyAccessor)
	{
		super(observableBean, propertyAccessor);
		
		this.bean = observableBean.getBean();
		initializeFactory();
		isDataSetCached = false;
	}
	
	private void initializeFactory()
	{
		org.robobinding.ItemPresentationModel annotation = getPropertyAccessor().getAnnotation(org.robobinding.ItemPresentationModel.class);
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
	public Object getValue()
	{
		cachedDataSet = super.getValue();
		isDataSetCached = true;
		return cachedDataSet;
	}
	
	@SuppressWarnings("unchecked")
	protected <DataSetType> DataSetType getCachedDataSet()
	{
		if(isDataSetCached)
		{
			return (DataSetType)cachedDataSet;
		}
		return (DataSetType)getValue();
	}
	
	protected boolean isDataSetNull()
	{
		return getCachedDataSet() == null;
	}
	
	@Override
	public void setValue(Object newValue)
	{
		throw new UnsupportedOperationException();
	}
	
	public ItemPresentationModel<T> newItemPresentationModel()
	{
		return factory.newItemPresentationModel();
	}
	public void updateItemPresentationModel(ItemPresentationModel<T> itemPresentationModel, int position)
	{
		T item = getItem(position);
		itemPresentationModel.updateData(position, item);
	}
}
