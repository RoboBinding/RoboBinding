package org.robobinding.customviewbinding;

import org.robobinding.util.Preconditions;
import org.robobinding.widgetaddon.DefaultViewAddOnFactory;
import org.robobinding.widgetaddon.ViewAddOn;
import org.robobinding.widgetaddon.ViewAddOnFactory;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class CustomViewBindingDescription {
	private final Class<?> viewType;
	private final ViewBindingApplier viewBindingApplier;
	private ViewAddOnFactory factory;

	CustomViewBindingDescription(Class<?> viewType, ViewBindingApplier bindingAttributeMapperApplier) {
		this.viewType = viewType;
		this.viewBindingApplier = bindingAttributeMapperApplier;
	}
	
	public CustomViewBindingDescription withViewAddOn(Class<? extends ViewAddOn> viewAddOnType) {
		checkViewAddOnType(viewAddOnType);
		
		return withViewAddOn(new DefaultViewAddOnFactory(viewAddOnType));
	}
	
	private void checkViewAddOnType(Class<? extends ViewAddOn> viewAddOnType) {
		Preconditions.checkNotNull(viewAddOnType, "viewAddOnType must not be null");
	}
	
	public CustomViewBindingDescription withViewAddOn(ViewAddOnFactory factory) {
		checkViewAddOnFactory(factory);
		
		this.factory = factory;
		return this;
	}
	
	private void checkViewAddOnFactory(ViewAddOnFactory factory) {
		Preconditions.checkNotNull(factory, "factory must not be null");
	}

	public CustomViewBindingApplier build() {
		return new CustomViewBindingApplier(viewType, viewBindingApplier, factory);
	}
}
