package org.robobinding.customviewbinding;

import java.util.List;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PriorityViewBinding<ViewType> implements ViewBinding<ViewType> {
	private final List<ViewBinding<ViewType>> viewBindings;
	
	public PriorityViewBinding(List<ViewBinding<ViewType>> viewBindings) {
		this.viewBindings = viewBindings;
	}
	
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<ViewType> mappings) {
		KeepFirstAttributes<ViewType> keepFirstAttributes = new KeepFirstAttributes<ViewType>(mappings);
		for(ViewBinding<ViewType> viewBinding : viewBindings) {
			viewBinding.mapBindingAttributes(keepFirstAttributes);
		}
	}
}
