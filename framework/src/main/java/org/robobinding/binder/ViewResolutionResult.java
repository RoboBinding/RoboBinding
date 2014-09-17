package org.robobinding.binder;

import org.robobinding.ViewResolutionErrors;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewResolutionResult {
	private ResolvedBindingAttributesForView resolvedBindingAttributes;
	private ViewResolutionErrors error;

	public ViewResolutionResult(ResolvedBindingAttributesForView resolvedBindingAttributes, ViewResolutionErrors error) {
		this.resolvedBindingAttributes = resolvedBindingAttributes;
		this.error = error;
	}

	public ResolvedBindingAttributesForView getResolvedBindingAttributes() {
		return resolvedBindingAttributes;
	}

	public void addPotentialErrorTo(ViewHierarchyInflationErrorsException errors) {
		errors.addViewResolutionError(error);
	}

	public void assertNoErrors() {
		error.assertNoErrors();
	}
}
