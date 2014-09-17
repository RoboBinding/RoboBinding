package org.robobinding.binder;

import java.util.List;

import org.robobinding.BindingContext;
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class InflatedView {
	final List<ResolvedBindingAttributesForView> childViewBindingAttributesGroup;
	private final ViewHierarchyInflationErrorsException errors;

	public InflatedView(List<ResolvedBindingAttributesForView> childViewBindingAttributesGroup, ViewHierarchyInflationErrorsException errors) {
		this.childViewBindingAttributesGroup = childViewBindingAttributesGroup;
		this.errors = errors;
	}

	public void bindChildViews(BindingContext bindingContext) {
		for (ResolvedBindingAttributesForView viewBindingAttributes : childViewBindingAttributesGroup) {
			errors.addViewBindingError(viewBindingAttributes.bindTo(bindingContext));
		}
	}

	public void assertNoErrors(ErrorFormatter errorFormatter) {
		errors.assertNoErrors(errorFormatter);
	}

	public void preinitializeViews(BindingContext bindingContext) {
		for (ResolvedBindingAttributesForView viewBindingAttributes : childViewBindingAttributesGroup) {
			viewBindingAttributes.preinitializeView(bindingContext);
		}
	}
}
