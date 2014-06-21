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
public class ViewListenersMap {
    final Map<Class<? extends View>, Class<? extends ViewListeners>> mappings;
    
    public ViewListenersMap(Map<Class<? extends View>, Class<? extends ViewListeners>> mappings) {
	this.mappings = newHashMap(mappings);
    }
    
    public boolean contains(Class<? extends View> viewClass) {
        return mappings.containsKey(viewClass);
    }
    
    public Class<? extends ViewListeners> getViewListenersClass(Class<? extends View> viewClass) {
        return mappings.get(viewClass);
    }
}
