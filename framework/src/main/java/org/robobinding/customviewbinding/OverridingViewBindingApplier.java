package org.robobinding.customviewbinding;

import org.robobinding.viewbinding.ViewBindingMap;
import org.robobinding.viewbinding.ViewBinding;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class OverridingViewBindingApplier<ViewType> implements ViewBindingApplier {
	private final Class<ViewType> viewClass;
	private final ViewBinding<ViewType> viewBinding;
	
	public OverridingViewBindingApplier(Class<ViewType> viewClass, ViewBinding<ViewType> viewBinding) {
		this.viewClass = viewClass;
		this.viewBinding = viewBinding;
	}

	@Override
	public void apply(ViewBindingMap viewBindingMap) {
		viewBindingMap.put(viewClass, viewBinding);
	}

}
