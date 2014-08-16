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
public class ViewListenersMap {
    final Map<Class<? extends View>, Class<? extends ViewListeners>> mappings;
    
    public ViewListenersMap(Map<Class<? extends View>, Class<? extends ViewListeners>> mappings) {
	this.mappings = Maps.newHashMap(mappings);
    }
    
    public boolean contains(Class<? extends View> viewClass) {
        return mappings.containsKey(viewClass);
    }
    
    public Class<? extends ViewListeners> getViewListenersClass(Class<? extends View> viewClass) {
        return mappings.get(viewClass);
    }
}
