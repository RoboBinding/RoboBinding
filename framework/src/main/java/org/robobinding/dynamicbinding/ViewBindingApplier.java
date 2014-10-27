package org.robobinding.dynamicbinding;

import org.robobinding.viewattribute.ViewListeners;
import org.robobinding.viewattribute.ViewListenersMapBuilder;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProvider;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMapBuilder;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewBindingApplier<T extends View> {
	private final Class<T> viewClass;
	private final Class<? extends ViewListeners> viewListenersClass;
	private final BindingAttributeMappingsProvider<T> bindingAttributeMappingsProvider;

	ViewBindingApplier(Class<T> viewClass, Class<? extends ViewListeners> viewListenersClass,
			BindingAttributeMappingsProvider<T> bindingAttributeMappingsProvider) {
		this.viewClass = viewClass;
		this.viewListenersClass = viewListenersClass;
		this.bindingAttributeMappingsProvider = bindingAttributeMappingsProvider;
	}

	public void applyBindingAttributeMapper(BindingAttributeMappingsProviderMapBuilder bindingAttributeProviderMapBuilder) {
		bindingAttributeProviderMapBuilder.put(viewClass, bindingAttributeMappingsProvider);
	}

	public void applyViewListenersIfExists(ViewListenersMapBuilder viewListenersMapBuilder) {
		if (viewListenersClass != null) {
			viewListenersMapBuilder.put(viewClass, viewListenersClass);
		}
	}
}
