package org.robobinding.widgetaddon;

import java.util.Map;

import org.robobinding.viewattribute.ViewTags;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAddOnsBuilder {
	private static final int VIEW_ADDON_KEY = ViewTags.USED_KEY2;
	private final Map<Class<?>, ViewAddOnFactory> mappings;

	public ViewAddOnsBuilder() {
		mappings = Maps.newHashMap();
	}

	public void put(Class<?> viewClass, ViewAddOnFactory factory) {
		mappings.put(viewClass, factory);
	}

	public ViewAddOns build() {
		ViewAddOnFactories factories = new ViewAddOnFactories(mappings);
		return new ViewTagsBasedViewAddOns(factories, new ViewTags<ViewAddOn>(VIEW_ADDON_KEY));
	}
}
