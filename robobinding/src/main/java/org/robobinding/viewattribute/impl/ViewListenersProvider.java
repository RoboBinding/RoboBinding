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
package org.robobinding.viewattribute.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.robobinding.util.ConstructorUtils;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.ViewListenersInjector;
import org.robobinding.viewattribute.view.ViewListeners;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.view.View;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@SuppressWarnings("unchecked")
class ViewListenersProvider implements ViewListenersInjector
{
	private static Map<Class<? extends ViewListenersAware<?>>, Class<? extends ViewListeners>> cachedViewListenersClasses = Maps.newHashMap();
	
	private Map<ViewAndViewListenersClassPair, ViewListeners> cachedViewListeners;
	
	public ViewListenersProvider()
	{
		cachedViewListeners = Maps.newHashMap();
	}

	@Override
	public void injectIfRequired(ViewAttribute viewAttribute, View view)
	{
		if(viewAttribute instanceof ViewListenersAware)
		{
			ViewListenersAware<ViewListeners> viewListenersAwareAttribute = (ViewListenersAware<ViewListeners>)viewAttribute;
			ViewListeners viewListeners = lookupViewListenersClass(view, (Class<? extends ViewListenersAware<?>>)viewListenersAwareAttribute.getClass());
			viewListenersAwareAttribute.setViewListeners(viewListeners);
		}
	}

	/**
	 * TODO: Will refactor later - Cheng.
	 */
	public ViewListeners forViewAndAttribute(View view, ViewListenersAware<?> viewListenersAware)
	{
		return lookupViewListenersClass(view, (Class<? extends ViewListenersAware<?>>)viewListenersAware.getClass());
	}

	private ViewListeners lookupViewListenersClass(View view, Class<? extends ViewListenersAware<?>> viewListenersAwareClass)
	{
		if (viewListenersClassIsKnown(viewListenersAwareClass))
			return loadViewListenersInstanceFromCache(view, viewListenersAwareClass);
		
		ParameterizedType viewListenersAwareParameterizedType = findViewListenersAwareParameterizedType(viewListenersAwareClass);

		if (viewListenersAwareParameterizedType != null)
			return createAndCacheNewViewListenersInstance(view, viewListenersAwareClass, viewListenersAwareParameterizedType);
		
		return findViewListenersClassForAttributeSuperClass(view, viewListenersAwareClass);
	}

	private <T extends ViewListeners, S> T loadViewListenersInstanceFromCache(View view, Class<S> viewListenersAwareClass)
	{
		Class<T> viewListenersClass = loadViewListenersClassFromCache(viewListenersAwareClass);
		
		ViewAndViewListenersClassPair viewAndViewListenersClassPair = new ViewAndViewListenersClassPair(view, viewListenersClass);
		
		if (viewIsNotObservedByViewListenersInstance(viewAndViewListenersClassPair))
			createAndCacheKnownViewListenersInstance(view, viewListenersClass, viewAndViewListenersClassPair);
		
		return (T)cachedViewListeners.get(viewAndViewListenersClassPair);
	}

	private <T, S> Class<T> loadViewListenersClassFromCache(Class<S> viewListenersAwareClass)
	{
		return (Class<T>)cachedViewListenersClasses.get(viewListenersAwareClass);
	}

	private <T extends ViewListeners> void createAndCacheKnownViewListenersInstance(View view, Class<T> viewListenersClass, ViewAndViewListenersClassPair viewAndViewListenersClassPair)
	{
		T viewListenersInstance = (T)instantiateViewListenersInstance(view, viewListenersClass);
		cachedViewListeners.put(viewAndViewListenersClassPair, viewListenersInstance);
	}

	private ViewListeners createAndCacheNewViewListenersInstance(View view, Class<? extends ViewListenersAware<?>> viewListenersAwareClass, ParameterizedType viewListenersAwareParameterizedType)
	{
		Class<? extends ViewListeners> viewListenersClass = determineViewListenersClass(viewListenersAwareParameterizedType);
		cachedViewListenersClasses.put(viewListenersAwareClass, viewListenersClass);
		ViewListeners viewListeners = instantiateViewListenersInstance(view, viewListenersClass);
		cachedViewListeners.put(new ViewAndViewListenersClassPair(view, viewListeners.getClass()), viewListeners);
		return viewListeners;
	}
	
	private boolean viewIsNotObservedByViewListenersInstance(ViewAndViewListenersClassPair viewAndViewListenersClassPair)
	{
		return !cachedViewListeners.containsKey(viewAndViewListenersClassPair);
	}

	private <S> boolean viewListenersClassIsKnown(Class<S> viewListenersAwareClass)
	{
		return cachedViewListenersClasses.containsKey(viewListenersAwareClass);
	}

	private <T> Class<T> determineViewListenersClass(ParameterizedType viewListenersAwareParameterizedType)
	{
		return (Class<T>)viewListenersAwareParameterizedType.getActualTypeArguments()[0];
	}
		
	private ViewListeners instantiateViewListenersInstance(View view, Class<? extends ViewListeners> viewListenersClass)
	{
		try
		{
			ViewListeners viewListeners = ConstructorUtils.invokeConstructor(viewListenersClass, view);
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

	private ViewListeners findViewListenersClassForAttributeSuperClass(View view, Class<? extends ViewListenersAware<?>> viewListenersAwareClass)
	{
		Class<? extends ViewListenersAware<?>> superclass = (Class<? extends ViewListenersAware<?>>)viewListenersAwareClass.getSuperclass();
		if (ViewListenersAware.class.isAssignableFrom(superclass))
			return lookupViewListenersClass(view, superclass);
		
		throw new RuntimeException("No class in hierarchy implements ViewListenersAware");
	}
	
	private boolean instanceOfViewListenersAware(ParameterizedType parameterizedType)
	{
		return parameterizedType.getRawType().getClass().isInstance(ViewListenersAware.class);
	}
	
	private static class ViewAndViewListenersClassPair
	{
		private View view;
		private Class<? extends ViewListeners> viewListenersClass;
		public ViewAndViewListenersClassPair(View view, Class<? extends ViewListeners> viewListenersClass)
		{
			this.view = view;
			this.viewListenersClass = viewListenersClass;
		}
		
		@Override
		public boolean equals(Object other)
		{
			if (this == other)
				return true;
			if (!(other instanceof ViewAndViewListenersClassPair))
				return false;
		
			final ViewAndViewListenersClassPair that = (ViewAndViewListenersClassPair) other;
			return Objects.equal(view, that.view) && Objects.equal(viewListenersClass, that.viewListenersClass);
		}
		
		@Override
		public int hashCode()
		{
			return Objects.hashCode(view, viewListenersClass);
		}
	}
}
