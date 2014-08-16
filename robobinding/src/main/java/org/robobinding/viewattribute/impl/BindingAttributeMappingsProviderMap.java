package org.robobinding.viewattribute.impl;

import java.util.Map;

import org.robobinding.internal.guava.Maps;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingAttributeMappingsProviderMap {
    final Map<Class<? extends View>, BindingAttributeMappingsProvider<? extends View>> mappings;
    
    public BindingAttributeMappingsProviderMap(
	    Map<Class<? extends View>, BindingAttributeMappingsProvider<? extends View>> mappings) {
	this.mappings = Maps.newHashMap(mappings);
    }
    
    public BindingAttributeMappingsProvider<? extends View> find(Class<? extends View> viewClass) {
        return mappings.get(viewClass);
    }
}
