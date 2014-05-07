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
    private final ViewListenersMap viewListenersMap;
    private final Map<View, ViewListeners> cachedViewListeners;

    public ViewListenersProvider(ViewListenersMap viewListenersMap) {
	this.viewListenersMap = viewListenersMap;
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
				+ "'; or did you forget to register viewListeners by org.robobinding.binder.BinderFactoryBuilder? The closest viewListeners '" 
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

	if (viewListenersMap.contains(viewClass)) {
	    return viewListenersMap.getViewListenersClass(viewClass);
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
