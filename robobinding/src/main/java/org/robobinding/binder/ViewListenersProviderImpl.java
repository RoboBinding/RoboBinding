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
import java.util.Map;

import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.internal.org_apache_commons_lang3.reflect.ConstructorUtils;
import org.robobinding.viewattribute.ViewListenersProvider;
import org.robobinding.viewattribute.compoundbutton.CompoundButtonListeners;
import org.robobinding.viewattribute.seekbar.SeekBarListeners;
import org.robobinding.viewattribute.view.ViewListeners;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewListenersProviderImpl implements ViewListenersProvider
{
	private static final Map<Class<? extends View>, Class<? extends ViewListeners>> viewListenersMap = createViewListenersMap();
	
	private Map<View, ViewListeners> cachedViewListeners;
	
	public ViewListenersProviderImpl()
	{
		cachedViewListeners = Maps.newHashMap();
	}
	
	@Override
	public ViewListeners forView(View view)
	{
		if(cachedViewListeners.containsKey(view))
		{
			return cachedViewListeners.get(view);
		}else
		{
			ViewListeners viewListeners = createViewListeners(view);
			cachedViewListeners.put(view, viewListeners);
			return viewListeners;
		}
	}
	
	private ViewListeners createViewListeners(View view)
	{
		Class<? extends ViewListeners> viewListenersClass = getViewListenersClass(view.getClass());
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
	
	private Class<? extends ViewListeners> getViewListenersClass(Class<? extends View> viewClass)
	{
		if(viewListenersMap.containsKey(viewClass))
		{
			Class<? extends ViewListeners> viewListenersClass = viewListenersMap.get(viewClass);
			return viewListenersClass;
		}else
		{
			@SuppressWarnings("unchecked")
			Class<? extends View> superViewClass = (Class<? extends View>)viewClass.getSuperclass();
			return getViewListenersClass(superViewClass);
		}
	}
	
	private static Map<Class<? extends View>, Class<? extends ViewListeners>> createViewListenersMap()
	{
		Map<Class<? extends View>, Class<? extends ViewListeners>> viewListenersMap = Maps.newHashMap();
		viewListenersMap.put(View.class, ViewListeners.class);
		viewListenersMap.put(CompoundButton.class, CompoundButtonListeners.class);
		viewListenersMap.put(SeekBar.class, SeekBarListeners.class);
		return viewListenersMap;
	}
}
