package org.robobinding.widgetaddon;

import java.util.Map;

import org.robobinding.util.SearchableClasses;
import org.robobinding.viewattribute.ViewTag;
import org.robobinding.viewattribute.ViewTags;

import android.view.View;

import com.google.common.collect.Maps;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
class ViewAddOnsImpl implements ViewAddOns{
	private final Map<Class<?>, ViewAddOnFactory> mappings;
	private final SearchableClasses searchableViewClasses;
	private final ViewTags<ViewAddOn> viewTags;

	public ViewAddOnsImpl(Map<Class<?>, ViewAddOnFactory> mappings, ViewTags<ViewAddOn> viewTags) {
		this.mappings = Maps.newHashMap(mappings);
		searchableViewClasses = new SearchableClasses(mappings.keySet());
		this.viewTags = viewTags;
	}
	
	@Override
	public ViewAddOn getMostSuitable(Object view) {
		if(supportsViewTag(view)) {
			return retrieveFromViewTag((View)view);
		} else {
			return createViewAddOn(view);
		}
	}
	
	private boolean supportsViewTag(Object view) {
		return view instanceof View;
	}
	
	private ViewAddOn retrieveFromViewTag(View view) {
		ViewTag<ViewAddOn> viewTag = viewTags.tagFor(view);
		if(viewTag.has()) {
			return viewTag.get();
		} else {
			ViewAddOn viewAddOn = createViewAddOn(view);
			viewTag.set(viewAddOn);
			return viewAddOn;
		}
		
	}

	private ViewAddOn createViewAddOn(Object view) {
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
