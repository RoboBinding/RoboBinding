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

import java.util.List;
import java.util.Map;

import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.internal.java_beans.PropertyDescriptor;
import org.robobinding.itempresentationmodel.TypedCursor;
import org.robobinding.presentationmodel.DependsOnStateOf;
import org.robobinding.presentationmodel.ItemPresentationModel;



/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyCreator
{
	private final ObservableBean observableBean;
	private Map<String, PropertyDescriptor> availableProperties;
	public PropertyCreator(Object bean)
	{
		this.observableBean = new ObservableBean(bean);
		initializeAvailablePropertyNames();
	}
	private void initializeAvailablePropertyNames()
	{
		availableProperties = Maps.newHashMap();

		List<PropertyDescriptor> propertyDescriptors = PropertyUtils.getPropertyDescriptors(observableBean.getBeanClass());
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) 
		{
			availableProperties.put(propertyDescriptor.getName(), propertyDescriptor);
		}
	}
	public <T> PropertyValueModel<T> createProperty(String propertyName)
	{
		PropertyAccessor<T> propertyAccessor = getPropertyAccessor(propertyName);
		AbstractProperty<T> property = new SimpleProperty<T>(observableBean, propertyAccessor);
		
		return convertAsDependencyPropertyIfNeccessary(property);
	}
	
	private <T> PropertyValueModel<T> convertAsDependencyPropertyIfNeccessary(AbstractProperty<T> property)
	{
		PropertyAccessor<T> propertyAccessor = property.getPropertyAccessor();
		if(propertyAccessor.hasAnnotation(DependsOnStateOf.class))
		{
			Dependency dependency = createDependency(propertyAccessor);
			return new DependencyProperty<T>(property, dependency);
		}
		return property;
	}
	
	private Dependency createDependency(PropertyAccessor<?> propertyAccessor)
	{
		return new Dependency(observableBean, propertyAccessor, availableProperties.keySet());
	}
	
	public <T> DataSetPropertyValueModel<T> createDataSetProperty(String propertyName)
	{
		PropertyAccessor<Object> propertyAccessor = getPropertyAccessor(propertyName);
		if(propertyAccessor.hasAnnotation(ItemPresentationModel.class))
		{
			AbstractDataSetProperty<T> dataSetProperty = null;
			if(List.class.isAssignableFrom(propertyAccessor.getPropertyType()))
			{
				dataSetProperty = new ListDataSetProperty<T>(observableBean, propertyAccessor);
			}else if(propertyAccessor.getPropertyType().isArray())
			{
				dataSetProperty = new ArrayDataSetProperty<T>(observableBean, propertyAccessor);
			}else if(TypedCursor.class.isAssignableFrom(propertyAccessor.getPropertyType()))
			{
				dataSetProperty = new CursorDataSetProperty<T>(observableBean, propertyAccessor);
			}else
			{
				throw new RuntimeException("The property '"+describeProperty(propertyName)+"' has an unsupported dataset type '"+propertyAccessor.getPropertyType()+"'");
			}
			
			return convertAsDataSetDependencyPropertyIfNeccessary(dataSetProperty);
		}else
		{
			throw new RuntimeException("The property '"+describeProperty(propertyName)+"' that provides the dataset is missing the @ItemPresentationModel annotation");
		}
	}
	
	private <T> DataSetPropertyValueModel<T> convertAsDataSetDependencyPropertyIfNeccessary(AbstractDataSetProperty<T> dataSetProperty)
	{
		PropertyAccessor<Object> propertyAccessor = dataSetProperty.getPropertyAccessor();
		if(propertyAccessor.hasAnnotation(DependsOnStateOf.class))
		{
			Dependency dependency = createDependency(propertyAccessor);
			return new DataSetDependencyProperty<T>(dataSetProperty, dependency);
		}
		return dataSetProperty;
	}
	
	private <T> PropertyAccessor<T> getPropertyAccessor(String propertyName)
	{
		PropertyDescriptor propertyDescriptor = availableProperties.get(propertyName);
		if(propertyDescriptor == null)
		{
			throw new RuntimeException("The property '"+describeProperty(propertyName)+"' does not exist");
		}
		return new PropertyAccessor<T>(propertyDescriptor, observableBean.getBeanClass());
	}
	private String describeProperty(String propertyName)
	{
		String beanClassName = observableBean.getBeanClassName();
		return beanClassName+"."+propertyName;
	}
}
