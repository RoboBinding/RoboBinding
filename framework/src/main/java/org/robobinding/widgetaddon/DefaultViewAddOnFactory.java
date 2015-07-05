package org.robobinding.widgetaddon;

import java.lang.reflect.InvocationTargetException;

import org.robobinding.util.ConstructorUtils;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DefaultViewAddOnFactory implements ViewAddOnFactory {
	private final Class<? extends ViewAddOn> viewAddOnType;
	
	public DefaultViewAddOnFactory(Class<? extends ViewAddOn> viewAddOnType) {
		this.viewAddOnType = viewAddOnType;
	}
	
	@Override
	public ViewAddOn create(Object view) {
		try {
			return ConstructorUtils.invokeConstructor(viewAddOnType, view);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("ViewAddOn class " + viewAddOnType.getName() + " does not have a constructor with a view parameter");
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException("ViewAddOn class " + viewAddOnType.getName() + " could not be instantiated: " + e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("ViewAddOn class " + viewAddOnType.getName() + " is not public");
		}

	}
}
