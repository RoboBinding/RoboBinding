package org.robobinding.viewattribute;

import java.util.Map;

import org.robobinding.util.SearchableClasses;

import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewListenersMap {
    private final Map<Class<?>, Class<? extends ViewListeners>> mappings;
    private final SearchableClasses searchableViewClasses;
    
    public ViewListenersMap(Map<Class<?>, Class<? extends ViewListeners>> mappings) {
	this.mappings = Maps.newHashMap(mappings);
	searchableViewClasses = new SearchableClasses(mappings.keySet());
    }
    
    public Class<? extends ViewListeners> findMostSuitable(Class<?> viewClass) {
	Class<?> foundViewClass = searchableViewClasses.findNearestAssignableFrom(viewClass);
	if (foundViewClass == null) {
	    return null;
	} else {
	    return mappings.get(foundViewClass);
	}
    }
}
