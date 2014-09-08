package org.robobinding.viewattribute.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.robobinding.internal.guava.Maps;
import org.robobinding.util.ConstructorUtils;
import org.robobinding.viewattribute.ViewListeners;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.ViewListenersInjector;
import org.robobinding.viewattribute.ViewListenersMap;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ViewListenersProvider implements ViewListenersInjector {
    private final ViewListenersMap viewListenersMap;
    private final Map<Object, ViewListeners> cachedViewListeners;

    public ViewListenersProvider(ViewListenersMap viewListenersMap) {
	this.viewListenersMap = viewListenersMap;
	cachedViewListeners = Maps.newHashMap();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void injectIfRequired(Object viewAttribute, Object view) {
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

    private ViewListeners getViewListeners(Object view) {
	if (cachedViewListeners.containsKey(view)) {
	    return cachedViewListeners.get(view);
	} else {
	    ViewListeners viewListeners = createViewListeners(view);
	    cachedViewListeners.put(view, viewListeners);
	    return viewListeners;
	}
    }

    private ViewListeners createViewListeners(Object view) {
	Class<?> viewClass = view.getClass();
	Class<? extends ViewListeners> viewListenersClass = viewListenersMap.findMostSuitable(viewClass);
	if (viewListenersClass == null) {
	    throw new RuntimeException("no ViewListeners registered for " + viewClass.getName());
	}
	
	return instantiateViewListenersInstance(view, viewListenersClass);
    }

    private ViewListeners instantiateViewListenersInstance(Object view, Class<? extends ViewListeners> viewListenersClass) {
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
