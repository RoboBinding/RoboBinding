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

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@SuppressWarnings("unchecked")
class ViewListenersProvider implements ViewListenersInjector {
    private final Map<Class<? extends View>, Class<? extends ViewListeners>> viewToViewListernersMap;
    private final Map<View, ViewListeners> cachedViewListeners;

    public ViewListenersProvider(Map<Class<? extends View>, Class<? extends ViewListeners>> viewToViewListenersMap) {
	this.viewToViewListernersMap = viewToViewListenersMap;
	cachedViewListeners = newHashMap();
    }

    @Override
    public void injectIfRequired(ViewAttribute viewAttribute, View view) {
	if (viewAttribute instanceof ViewListenersAware) {
	    ViewListeners viewListeners = getViewListeners(view);

	    ViewListenersAware<ViewListeners> viewListenersAwareAttribute = (ViewListenersAware<ViewListeners>) viewAttribute;
	    viewListenersAwareAttribute.setViewListeners(viewListeners);
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

    private Class<? extends ViewListeners> getMostSuitableViewListenersClass(Class<?> viewClass) {
	if (viewClass == View.class) {
	    return ViewListeners.class;
	}

	if (viewToViewListernersMap.containsKey(viewClass)) {
	    return viewToViewListernersMap.get(viewClass);
	} else {
	    Class<?> viewSuperClass = viewClass.getSuperclass();
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
