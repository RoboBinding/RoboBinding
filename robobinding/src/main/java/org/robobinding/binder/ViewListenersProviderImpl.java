/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.robobinding.viewattribute.ViewListenersProvider;
import org.robobinding.viewattribute.view.ViewListeners;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.view.View;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("unchecked")
public class ViewListenersProviderImpl implements ViewListenersProvider
{
	private Map<View, ViewListeners> cachedViewListeners;

	public ViewListenersProviderImpl()
	{
		cachedViewListeners = Maps.newHashMap();
	}

	@Override
	public <T extends ViewListeners> T forViewAndAttribute(View view, ViewListenersAware<T> viewListenersAware)
	{
		if (cachedViewListeners.containsKey(view))
		{
			return (T) cachedViewListeners.get(view);
		} else
		{
			T viewListeners = (T)getViewListenersClass(view, viewListenersAware.getClass());
			cachedViewListeners.put(view, viewListeners);
			return viewListeners;
		}
	}

	private <S extends ViewListeners, T extends ViewListenersAware<S>> S getViewListenersClass(View view, Class<T> viewAttribute)
	{
		ParameterizedType viewListenersAwareParameterizedType = findViewListenersAwareParameterizedType(viewAttribute);

		if (viewListenersAwareParameterizedType != null)
			return instantiateViewListenersInstance(view, viewListenersAwareParameterizedType);
		
		return getViewListenersClassFromAttributeSuperClass(view, viewAttribute);
	}

	private <S> S instantiateViewListenersInstance(View view, ParameterizedType viewListenersAwareParameterizedType)
	{
		try
		{
			S viewListeners = ConstructorUtils.invokeConstructor((Class<S>)viewListenersAwareParameterizedType.getActualTypeArguments()[0], view);
			return viewListeners;
		} catch (NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		} catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		} catch (InvocationTargetException e)
		{
			throw new RuntimeException(e);
		} catch (InstantiationException e)
		{
			throw new RuntimeException(e);
		}
	}

	private <T> ParameterizedType findViewListenersAwareParameterizedType(Class<T> viewAttribute)
	{
		Type[] genericInterfaces = viewAttribute.getGenericInterfaces();

		for (Type interfaceType : genericInterfaces)
		{
			if (interfaceType instanceof ParameterizedType)
			{
				ParameterizedType parameterizedType = (ParameterizedType) interfaceType;
				if (instanceOfViewListenersAware(parameterizedType))
					return parameterizedType;
			}
		}
		
		return null;
	}

	private <S extends ViewListeners, T extends ViewListenersAware<S>> S getViewListenersClassFromAttributeSuperClass(View view, Class<T> viewAttribute)
	{
		Class<? super T> superclass = viewAttribute.getSuperclass();
		if (ViewListenersAware.class.isAssignableFrom(superclass))
			return getViewListenersClass(view, (Class<T>)viewAttribute.getSuperclass());
		
		throw new RuntimeException("No class in hierachy implements ViewListenersAware");
	}
	
	private boolean instanceOfViewListenersAware(ParameterizedType parameterizedType)
	{
		return parameterizedType.getRawType().getClass().isInstance(ViewListenersAware.class);
	}
}
