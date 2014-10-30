package org.robobinding.viewbinding;

import java.util.Map;

import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactory;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewBindingMap {
	private final Map<Class<?>, ViewBinding<?>> mappings;

	public ViewBindingMap() {
		mappings = Maps.newHashMap();
	}

	public <ViewType> ViewBindingMap put(Class<ViewType> viewClass, ViewBinding<ViewType> viewBinding) {
		mappings.put(viewClass, viewBinding);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public  <ViewType> ViewBinding<ViewType> find(Class<ViewType> viewClass) {
		return (ViewBinding<ViewType>)mappings.get(viewClass);
	}

	public InitializedBindingAttributeMappingsProviders buildInitializedBindingAttributeMappingsProviders(
			ViewAttributeBinderFactory viewAttributeBinderFactory) {
		return new InitializedBindingAttributeMappingsProviders(mappings, viewAttributeBinderFactory);
	}
}
