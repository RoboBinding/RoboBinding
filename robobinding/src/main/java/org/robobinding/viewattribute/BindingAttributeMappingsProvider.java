package org.robobinding.viewattribute;

import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;
import org.robobinding.viewattribute.impl.ViewAttributeInitializer;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public interface BindingAttributeMappingsProvider<T extends View> {
    BindingAttributeMappingsImpl<T> createBindingAttributeMappings(ViewAttributeInitializer viewAttributeInitializer);
}
