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

import org.robobinding.internal.org_apache_commons_lang3.Validate;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class ObservableBean implements ObservableProperties
{
	private Object bean;

	public ObservableBean(Object bean)
	{
		this.bean = bean;
	}

	public boolean isObservable()
	{
		return bean instanceof ObservableProperties;
	}

	public Class<?> getBeanClass()
	{
		return bean.getClass();
	}
	
	public String getBeanClassName()
	{
		return bean.getClass().getName();
	}
	
	@Override
	public void addPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener)
	{
		checkObservable(propertyName);
		asObservableBean().addPropertyChangeListener(propertyName, listener);
	}

	@Override
	public void removePropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener)
	{
		checkObservable(propertyName);
		asObservableBean().removePropertyChangeListener(propertyName, listener);
	}

	private ObservableProperties asObservableBean()
	{
		return (ObservableProperties) bean;
	}

	private void checkObservable(String propertyName)
	{
		Validate.isTrue(
				isObservable(),
				"You are binding to property '"+propertyName+"' but presentation model '"+bean.getClass().getName()+"' is not observable. You either have to annotate your presentation model with @PresentationModel or implement interface ObservableProperties");
	}
}
