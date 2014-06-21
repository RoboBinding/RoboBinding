package org.robobinding.viewattribute.impl;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;


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
	this.mappings = newHashMap(mappings);
    }
    
    public BindingAttributeMappingsProvider<? extends View> find(Class<? extends View> viewClass) {
        return mappings.get(viewClass);
    }
}
