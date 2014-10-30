package org.robobinding.viewbinding;

import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactory;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewBindingAdapter implements InitializedBindingAttributeMappingsProvider {
	private final ViewBinding<Object> viewBinding;
	private final ViewAttributeBinderFactory viewAttributeBinderFactory;

	@SuppressWarnings("unchecked")
	public ViewBindingAdapter(ViewBinding<?> viewBinding, ViewAttributeBinderFactory viewAttributeBinderFactory) {
		this.viewBinding = (ViewBinding<Object>)viewBinding;
		this.viewAttributeBinderFactory = viewAttributeBinderFactory;
	}

	public InitailizedBindingAttributeMappings create() {
		BindingAttributeMappingsImpl<Object> bindingAttributeMappings = new BindingAttributeMappingsImpl<Object>(viewAttributeBinderFactory);
		viewBinding.mapBindingAttributes(bindingAttributeMappings);
		return bindingAttributeMappings.createInitailizedBindingAttributeMappings();
	}
}
