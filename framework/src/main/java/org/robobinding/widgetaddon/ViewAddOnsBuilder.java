package org.robobinding.widgetaddon;

import java.util.Map;

import org.robobinding.viewattribute.ViewTagger;
import org.robobinding.viewattribute.ViewTags;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAddOnsBuilder {
	private static final int VIEW_ADDON_KEY = ViewTagger.KEY2;
	private final Map<Class<?>, ViewAddOnFactory> mappings;

	public ViewAddOnsBuilder() {
		mappings = Maps.newHashMap();
	}

	public void put(Class<?> viewClass, ViewAddOnFactory factory) {
		mappings.put(viewClass, factory);
	}

	public ViewAddOns build() {
		return new ViewAddOnsImpl(mappings, new ViewTags<ViewAddOn>(VIEW_ADDON_KEY));
	}
}
