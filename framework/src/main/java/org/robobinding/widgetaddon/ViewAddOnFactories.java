package org.robobinding.widgetaddon;

import java.util.Map;

import org.robobinding.util.Maps;
import org.robobinding.util.SearchableClasses;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
class ViewAddOnFactories {
	private final Map<Class<?>, ViewAddOnFactory> mappings;
	private final SearchableClasses searchableViewClasses;

	public ViewAddOnFactories(Map<Class<?>, ViewAddOnFactory> mappings) {
		this.mappings = Maps.newHashMap(mappings);
		searchableViewClasses = new SearchableClasses(mappings.keySet());
	}
	
	public ViewAddOn createViewAddOn(Object view) {
		Class<?> viewClass = view.getClass();
		ViewAddOnFactory factory = findMostSuitable(viewClass);
		if (factory == null) {
			throw new RuntimeException("no ViewAddOn registered for " + viewClass.getName());
		}

		return factory.create(view);
	}

	private ViewAddOnFactory findMostSuitable(Class<?> viewClass) {
		Class<?> foundViewClass = searchableViewClasses.findNearestAssignableFrom(viewClass);
		if (foundViewClass == null) {
			return null;
		} else {
			return mappings.get(foundViewClass);
		}
	}
}
