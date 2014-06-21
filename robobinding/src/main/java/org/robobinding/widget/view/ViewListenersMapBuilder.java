package org.robobinding.widget.view;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewListenersMapBuilder {
    private final Map<Class<? extends View>, Class<? extends ViewListeners>> mappings;
    
    public ViewListenersMapBuilder() {
	mappings = newHashMap();
    }
    
    public ViewListenersMapBuilder put(Class<? extends View> viewClass, Class<? extends ViewListeners> viewListenersClass) {
	mappings.put(viewClass, viewListenersClass);
	return this;
    }
    
    public ViewListenersMap build() {
	return new ViewListenersMap(mappings);
    }
}
