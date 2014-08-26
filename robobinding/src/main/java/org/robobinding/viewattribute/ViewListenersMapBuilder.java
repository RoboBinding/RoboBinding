package org.robobinding.viewattribute;

import java.util.Map;

import org.robobinding.internal.guava.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewListenersMapBuilder {
    private final Map<Class<?>, Class<? extends ViewListeners>> mappings;
    
    public ViewListenersMapBuilder() {
	mappings = Maps.newHashMap();
    }
    
    @SuppressWarnings("unchecked")
    public void put(Class<?> viewClass, Class<? extends ViewListeners> viewListenersClass) {
	mappings.put(viewClass, (Class<ViewListeners>) viewListenersClass);
    }
    
    public ViewListenersMap build() {
	return new ViewListenersMap(mappings);
    }
}
