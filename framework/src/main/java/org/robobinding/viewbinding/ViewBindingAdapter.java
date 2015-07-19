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

	@SuppressWarnings("unchecked")
	public ViewBindingAdapter(ViewBinding<?> viewBinding) {
		this.viewBinding = (ViewBinding<Object>)viewBinding;
	}

	public InitailizedBindingAttributeMappings create(ViewAttributeBinderFactory viewAttributeBinderFactory) {
		BindingAttributeMappingsImpl<Object> bindingAttributeMappings = new BindingAttributeMappingsImpl<Object>(viewAttributeBinderFactory);
		viewBinding.mapBindingAttributes(bindingAttributeMappings);
		return bindingAttributeMappings.createInitailizedBindingAttributeMappings();
	}
}
