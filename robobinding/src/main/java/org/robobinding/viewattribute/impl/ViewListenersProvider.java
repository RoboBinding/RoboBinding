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

import static com.google.common.collect.Maps.newHashMap;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.robobinding.util.ConstructorUtils;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.ViewListenersInjector;
import org.robobinding.viewattribute.view.ViewListeners;
import org.robobinding.viewattribute.view.ViewListenersAware;
import org.robobinding.viewattribute.view.ViewListenersMap;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
class ViewListenersProvider implements ViewListenersInjector {
    private final ViewListenersMap viewListernersMap;
    private final Map<View, ViewListeners> cachedViewListeners;

    public ViewListenersProvider(ViewListenersMap viewListernersMap) {
	this.viewListernersMap = viewListernersMap;
	cachedViewListeners = newHashMap();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void injectIfRequired(ViewAttribute viewAttribute, View view) {
	if (viewAttribute instanceof ViewListenersAware) {
	    ViewListeners viewListeners = getViewListeners(view);

	    ViewListenersAware<ViewListeners> viewListenersAwareAttribute = (ViewListenersAware<ViewListeners>) viewAttribute;
	    
	    try {
	    viewListenersAwareAttribute.setViewListeners(viewListeners);
	    } catch (ClassCastException e) {
		throw new RuntimeException(
			"Is '" + viewAttribute.getClass().getName() + "' a view attribute of view '" + view.getClass().getName() 
				+ "'; or forget to register viewListeners by org.robobinding.binder.BinderFactoryBuilder? Seems the closest viewListeners '" 
				+ viewListeners.getClass().getName() + "' we found does not match the view attribute.",
			e);
	    }
	}
    }

    private ViewListeners getViewListeners(View view) {
	if (cachedViewListeners.containsKey(view)) {
	    return cachedViewListeners.get(view);
	} else {
	    ViewListeners viewListeners = createViewListeners(view);
	    cachedViewListeners.put(view, viewListeners);
	    return viewListeners;
	}
    }

    private ViewListeners createViewListeners(View view) {
	Class<? extends ViewListeners> viewListenersClass = getMostSuitableViewListenersClass(view.getClass());
	return instantiateViewListenersInstance(view, viewListenersClass);
    }

    @SuppressWarnings("unchecked")
    private Class<? extends ViewListeners> getMostSuitableViewListenersClass(Class<? extends View> viewClass) {
	if (viewClass == View.class) {
	    return ViewListeners.class;
	}

	if (viewListernersMap.contains(viewClass)) {
	    return viewListernersMap.getViewListenersClass(viewClass);
	} else {
	    Class<? extends View> viewSuperClass = (Class<? extends View>) viewClass.getSuperclass();
	    return getMostSuitableViewListenersClass(viewSuperClass);
	}
    }

    private ViewListeners instantiateViewListenersInstance(View view, Class<? extends ViewListeners> viewListenersClass) {
	try {
	    ViewListeners viewListeners = ConstructorUtils.invokeConstructor(viewListenersClass, view);
	    return viewListeners;
	} catch (NoSuchMethodException e) {
	    throw new RuntimeException(e);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException(e);
	} catch (InvocationTargetException e) {
	    throw new RuntimeException(e);
	} catch (InstantiationException e) {
	    throw new RuntimeException(e);
	}
    }
}
