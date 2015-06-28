package org.robobinding.customviewbinding;

import org.robobinding.viewbinding.ViewBindingMap;
import org.robobinding.widgetaddon.ViewAddOnFactory;
import org.robobinding.widgetaddon.ViewAddOnsBuilder;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CustomViewBindingApplier {
	private final Class<?> viewType;
	private final ViewAddOnFactory factory;
	private final ViewBindingApplier bindingAttributeMapperApplier;

	CustomViewBindingApplier(Class<?> viewType, ViewBindingApplier bindingAttributeMapperApplier, 
			ViewAddOnFactory factory) {
		this.viewType = viewType;
		this.bindingAttributeMapperApplier = bindingAttributeMapperApplier;
		this.factory = factory;
	}

	public void applyBindingAttributeMapper(ViewBindingMap bindingAttributeProviderMapBuilder) {
		bindingAttributeMapperApplier.apply(bindingAttributeProviderMapBuilder);
	}

	public void applyViewAddOnIfExists(ViewAddOnsBuilder builder) {
		if (factory != null) {
			builder.put(viewType, factory);
		}
	}
}