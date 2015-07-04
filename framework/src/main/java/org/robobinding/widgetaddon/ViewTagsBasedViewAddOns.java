package org.robobinding.widgetaddon;

import org.robobinding.viewattribute.ViewTag;
import org.robobinding.viewattribute.ViewTags;

import android.view.View;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
class ViewTagsBasedViewAddOns implements ViewAddOns{
	private final ViewAddOnFactories viewAddOnFactories;
	private final ViewTags<ViewAddOn> viewTags;

	public ViewTagsBasedViewAddOns(ViewAddOnFactories viewAddOnFactories, ViewTags<ViewAddOn> viewTags) {
		this.viewAddOnFactories = viewAddOnFactories;
		this.viewTags = viewTags;
	}
	
	@Override
	public ViewAddOn getMostSuitable(Object view) {
		if(supportsViewTag(view)) {
			return retrieveFromViewTag((View)view);
		} else {
			return viewAddOnFactories.createViewAddOn(view);
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
			ViewAddOn viewAddOn = viewAddOnFactories.createViewAddOn(view);
			viewTag.set(viewAddOn);
			return viewAddOn;
		}
		
	}

}
