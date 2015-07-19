package org.robobinding.codegen.viewbinding;

import org.robobinding.codegen.apt.TypeElementFilter;
import org.robobinding.codegen.apt.element.WrappedTypeElement;
import org.robobinding.customviewbinding.CustomViewBinding;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingFilter implements TypeElementFilter {
	@Override
	public boolean include(WrappedTypeElement element) {
		return element.isAssignableTo(CustomViewBinding.class) && element.isConcreteClass();
	}
}
