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
package org.robobinding.itempresentationmodel;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.internal.java_beans.PropertyDescriptor;
import org.robobinding.internal.org_apache_commons_lang3.Validate;
import org.robobinding.property.PropertyAccessor;
import org.robobinding.property.PropertyUtils;

import android.database.AbstractCursor;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BeanCursor<T> extends AbstractCursor implements TypedCursor<T>
{
	
	private List<T> beans;
	private Map<String, PropertyAccessor<?>> propertyNameToAccessors;
	public BeanCursor(Collection<T> beans, Class<T> beanClass)
	{
		Validate.notNull(beans, "beans can not be null");
		Validate.notNull(beanClass, "beanClass can not be null");
		
		this.beans = Lists.newArrayList(beans);
		initializeProperties(beanClass);
	}
	private void initializeProperties(Class<T> beanClass)
	{
		List<PropertyDescriptor> propertyDescriptors = PropertyUtils.getPropertyDescriptors(beanClass);
		propertyNameToAccessors = Maps.newHashMap();
		for(PropertyDescriptor propertyDescriptor : propertyDescriptors)
		{
			PropertyAccessor<?> propertyAccessor = new PropertyAccessor<T>(propertyDescriptor, beanClass);
			propertyNameToAccessors.put(propertyDescriptor.getName(), propertyAccessor);
		}
	}
	@Override
	public int getCount()
	{
		return beans.size();
	}
	private transient volatile String[] propertyNamesCache;
	@Override
	public String[] getColumnNames()
	{
		if(propertyNamesCache == null)
		{
			Set<String> propertyNames = propertyNameToAccessors.keySet();
			propertyNamesCache = propertyNames.toArray(new String[0]);
		}
		return propertyNamesCache;
	}
	@Override
	public String getString(int column)
	{
		return (String)getColumnValue(column);
	}

	@Override
	public short getShort(int column)
	{
		return (Short)getColumnValue(column);
	}

	@Override
	public int getInt(int column)
	{
		return (Integer)getColumnValue(column);
	}

	@Override
	public long getLong(int column)
	{
		return (Long)getColumnValue(column);
	}

	@Override
	public float getFloat(int column)
	{
		return (Float)getColumnValue(column);
	}

	@Override
	public double getDouble(int column)
	{
		return (Double)getColumnValue(column);
	}

	@Override
	public boolean isNull(int column)
	{
		Object value = getColumnValue(column);
		return value == null;
	}
	private Object getColumnValue(int column)
	{
		Validate.isTrue(column < getNumColumns(), "column '"+column+"' is out of bound");
		PropertyAccessor<?> propertyAccesor = mapColumnToPropertyAccessor(column);
		return propertyAccesor.getValue(getBean());
	}
	private PropertyAccessor<?> mapColumnToPropertyAccessor(int column)
	{
		String propertyName = mapColumnToPropertyName(column);
		PropertyAccessor<?> propertyAccesor = propertyNameToAccessors.get(propertyName);
		return propertyAccesor;
	}
	private String mapColumnToPropertyName(int column)
	{
		return getColumnName(column);
	}
	private int getNumColumns()
	{
		return propertyNameToAccessors.size();
	}
	private Object getBean()
	{
		return beans.get(getPosition());
	}
	@Override
	public T getObjectAtPosition(int position)
	{
		Validate.isTrue(position < getCount(), "Invalid requested position '"+position+"', as the cursor size is '"+getCount()+"'");
		return beans.get(position);
	}
}
