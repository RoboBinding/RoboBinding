package org.robobinding.customviewbinding;


import java.util.List;

import org.robobinding.util.Lists;
import org.robobinding.viewbinding.ViewBinding;
import org.robobinding.viewbinding.ViewBindingMap;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ExtensionViewBindingApplier<ViewType> implements ViewBindingApplier {
	private final Class<ViewType> viewClass;
	private final ViewBinding<ViewType> viewBinding;
	
	public ExtensionViewBindingApplier(Class<ViewType> viewClass, ViewBinding<ViewType> viewBinding) {
		this.viewClass = viewClass;
		this.viewBinding = viewBinding;
	}


	@Override
	public void apply(final ViewBindingMap viewBindingMap) {
		List<ViewBinding<ViewType>> viewBindings = Lists.newArrayList();
		viewBindings.add(viewBinding);
		
		ViewBinding<ViewType> old = viewBindingMap.find(viewClass);
		if(old != null) {
			viewBindings.add(old);
		}
		
		viewBindingMap.put(viewClass, new PriorityViewBinding<ViewType>(viewBindings));
	}
}
