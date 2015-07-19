package org.robobinding.customviewbinding;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import com.google.common.base.Preconditions;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class CustomViewBinding<ViewType> {
	
	public void mapBindingAttributes(BindingAttributeMappings<ViewType> mappings) {
	}
	
	public final CustomViewBindingDescription extend(Class<ViewType> viewClass) {
		checkViewClass(viewClass);
		
		ViewBinding<ViewType> viewBinding = loadViewBinding();
		return new CustomViewBindingDescription(viewClass, 
				new ExtensionViewBindingApplier<ViewType>(viewClass, viewBinding));
	}

	private void checkViewClass(Class<ViewType> viewClass) {
		Preconditions.checkNotNull(viewClass, "viewClass must not be null");
	}

	private ViewBinding<ViewType> loadViewBinding() {
		ViewBindingLoader loader = new ViewBindingLoader();
		return loader.load(this);
	}
	
	public final CustomViewBindingDescription forView(final Class<ViewType> viewClass) {
		checkViewClass(viewClass);
		
		ViewBinding<ViewType> viewBinding = loadViewBinding();
		return new CustomViewBindingDescription(viewClass, 
				new OverridingViewBindingApplier<ViewType>(viewClass, viewBinding));
	}
}
