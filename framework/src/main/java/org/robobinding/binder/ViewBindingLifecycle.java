package org.robobinding.binder;

import org.robobinding.BindingContext;
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewBindingLifecycle {
	private final ErrorFormatter errorFormatter;

	public ViewBindingLifecycle(ErrorFormatter errorFormatter) {
		this.errorFormatter = errorFormatter;
	}

	public void run(InflatedView inflatedView, BindingContext bindingContext) {
		/*BindingContext bindingContext = bindingContextFactory.create(presentationModel);*/

		inflatedView.bindChildViews(bindingContext);
		inflatedView.assertNoErrors(errorFormatter);

		if (bindingContext.shouldPreInitializeViews()) {
			inflatedView.preinitializeViews(bindingContext);
		}
	}
}
