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
abstract class AbstractDataSetProperty<T> extends AbstractProperty<Object> implements DataSetPropertyValueModel<T>, PresentationModelPropertyChangeListener
{
	ItemPresentationModelFactory<T> factory;
	private boolean isDataSetNotInitialized;
	private Object dataSet;

	protected AbstractDataSetProperty(ObservableBean observableBean, PropertyAccessor<Object> propertyAccessor)
	{
		super(observableBean, propertyAccessor);
		
		initializeFactory();
		
		isDataSetNotInitialized = true;
		addPropertyChangeListener(this);
	}
	
	private void initializeFactory()
	{
		org.robobinding.presentationmodel.ItemPresentationModel annotation = getPropertyAccessor().getAnnotation(org.robobinding.presentationmodel.ItemPresentationModel.class);
		@SuppressWarnings("unchecked")
		Class<? extends ItemPresentationModel<T>> itemPresentationModelClass = (Class<? extends ItemPresentationModel<T>>)annotation.value();
		String factoryMethod = annotation.factoryMethod();
		if(StringUtils.isBlank(factoryMethod))
		{
			factory = new DefaultConstructorImpl<T>(itemPresentationModelClass);
		}else
		{
			factory = new FactoryMethodImpl<T>(getBean(), itemPresentationModelClass, factoryMethod);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected <DataSetType> DataSetType getDataSet()
	{
		if(isDataSetNotInitialized)
		{
			updateDataSet();
			isDataSetNotInitialized = false;
		}
		return (DataSetType)dataSet;
	}
	
	private void updateDataSet()
	{
		dataSet = super.getValue();
	}

	protected boolean isDataSetNull()
	{
		return getDataSet() == null;
	}
	
	@Override
	public ItemPresentationModel<T> newItemPresentationModel()
	{
		return factory.newItemPresentationModel();
	}
	
	@Override
	public final void propertyChanged()
	{
		updateDataSet();
	}
}
