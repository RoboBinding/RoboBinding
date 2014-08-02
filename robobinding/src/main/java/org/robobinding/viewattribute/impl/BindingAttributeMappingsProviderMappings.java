package org.robobinding.viewattribute.impl;

import android.view.View;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface BindingAttributeMappingsProviderMappings {
    <T extends View> void put(Class<T> viewClass, BindingAttributeMappingsProvider<T> bindingAttributeMappingsProvider);
}
