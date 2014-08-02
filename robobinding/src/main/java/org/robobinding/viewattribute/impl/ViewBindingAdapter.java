package org.robobinding.viewattribute.impl;

import org.robobinding.viewattribute.ViewBinding;

import android.view.View;

import com.google.common.base.Objects;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewBindingAdapter<T extends View> implements BindingAttributeMappingsProvider<T> {
    private final ViewBinding<T> viewBinding;

    public ViewBindingAdapter(ViewBinding<T> viewBinding) {
	this.viewBinding = viewBinding;
    }

    public InitailizedBindingAttributeMappings<T> createBindingAttributeMappings() {
	BindingAttributeMappingsImpl<T> bindingAttributeMappings = new BindingAttributeMappingsImpl<T>();
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
	final ViewBindingAdapter<T> that = (ViewBindingAdapter<T>) other;
	return Objects.equal(viewBinding, that.viewBinding);
    }

    @Override
    public int hashCode() {
	return Objects.hashCode(viewBinding);
    }
}
