package org.robobinding.binder;

import org.robobinding.BindingContext;
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewBindingLifecycle {
	private final BindingContextFactory bindingContextFactory;
	private final ErrorFormatter errorFormatter;

	public ViewBindingLifecycle(BindingContextFactory bindingContextFactory, ErrorFormatter errorFormatter) {
		this.bindingContextFactory = bindingContextFactory;
		this.errorFormatter = errorFormatter;
	}

	public void run(InflatedView inflatedView, AbstractPresentationModelObject presentationModel) {
		BindingContext bindingContext = bindingContextFactory.create(presentationModel);

		inflatedView.bindChildViews(bindingContext);
		inflatedView.assertNoErrors(errorFormatter);

		if (bindingContext.shouldPreInitializeViews()) {
			inflatedView.preinitializeViews(bindingContext);
		}
	}
}
