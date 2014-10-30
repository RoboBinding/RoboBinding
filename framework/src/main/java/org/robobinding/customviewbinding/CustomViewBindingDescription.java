package org.robobinding.customviewbinding;

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
	
	public CustomViewBindingDescription withViewAddOn(ViewAddOnFactory factory) {
		this.factory = factory;
		return this;
	}
	
	public CustomViewBindingApplier build() {
		return new CustomViewBindingApplier(viewType, viewBindingApplier, factory);
	}
}
