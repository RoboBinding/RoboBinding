package org.robobinding.viewattribute.impl;

import org.robobinding.viewattribute.ViewBinding;

import com.google.common.base.Objects;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewBindingAdapter<ViewType> implements BindingAttributeMappingsProvider<ViewType> {
	private final ViewBinding<ViewType> viewBinding;

	public ViewBindingAdapter(ViewBinding<ViewType> viewBinding) {
		this.viewBinding = viewBinding;
	}

	public InitailizedBindingAttributeMappings<ViewType> createBindingAttributeMappings() {
		BindingAttributeMappingsImpl<ViewType> bindingAttributeMappings = new BindingAttributeMappingsImpl<ViewType>();
		viewBinding.mapBindingAttributes(bindingAttributeMappings);
		return bindingAttributeMappings.createInitailizedBindingAttributeMappings();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof ViewBindingAdapter))
			return false;

		@SuppressWarnings("unchecked")
		final ViewBindingAdapter<ViewType> that = (ViewBindingAdapter<ViewType>) other;
		return Objects.equal(viewBinding, that.viewBinding);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(viewBinding);
	}
}
