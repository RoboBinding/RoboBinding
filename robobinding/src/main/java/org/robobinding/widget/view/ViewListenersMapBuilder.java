package org.robobinding.widget.view;

import java.util.Map;

import org.robobinding.internal.guava.Maps;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewListenersMapBuilder implements ViewListenersMappings {
    private final Map<Class<? extends View>, Class<? extends ViewListeners>> mappings;
    
    public ViewListenersMapBuilder() {
	mappings = Maps.newHashMap();
    }
    
    public void put(Class<? extends View> viewClass, Class<? extends ViewListeners> viewListenersClass) {
	mappings.put(viewClass, viewListenersClass);
    }
    
    
    
    public ViewListenersMap build() {
	return new ViewListenersMap(mappings);
    }
}
