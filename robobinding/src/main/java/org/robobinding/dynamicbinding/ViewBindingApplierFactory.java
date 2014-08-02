package org.robobinding.dynamicbinding;

import org.robobinding.viewattribute.impl.BindingAttributeMappingsProvider;
import org.robobinding.widget.view.ViewListeners;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class ViewBindingApplierFactory<T extends View> {
    private final Class<T> viewClass;

    public ViewBindingApplierFactory(Class<T> viewClass) {
	this.viewClass = viewClass;
    }

    public ViewBindingApplier<T> create(BindingAttributeMappingsProvider<T> bindingAttributeMappingsProvider, 
	    Class<? extends ViewListeners> viewListenersClass) {
	return new ViewBindingApplier<T>(viewClass, viewListenersClass, bindingAttributeMappingsProvider);
    }
}
