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
package org.robobinding.binder;


import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
abstract class AbstractViewAttributeMapping<T extends View>
{
	public abstract void initializeAndResolveAttribute(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews);
	
	protected <S> S createNewInstance(Class<S> clazz)
	{
		try
		{
			S newInstance = clazz.newInstance();
			return newInstance;
		} 
		catch (InstantiationException e)
		{
			throw new RuntimeException("Attribute class: " + clazz.getName() + " does not have an empty default constructor");
		} 
		catch (IllegalAccessException e)
		{
			throw new RuntimeException("Attribute class: " + clazz.getName() + " is not public");
		}
	}
}
