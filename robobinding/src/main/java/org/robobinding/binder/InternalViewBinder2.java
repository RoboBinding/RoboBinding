package org.robobinding.binder;

import org.robobinding.ViewBinderImplementor;
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;

import android.view.View;

public class InternalViewBinder2 extends InternalBinder implements
		ViewBinderImplementor {

	public InternalViewBinder2(BindingViewInflater bindingViewInflater,
			BindingContextFactory bindingContextFactory,
			ErrorFormatter errorFormatter) {
		super(bindingViewInflater, bindingContextFactory, errorFormatter);
	}

	@Override
	public View bind(View view, Object presentationModel) {
		InflatedView inflatedView = bindingViewInflater.wrapView(view);
		return bind(inflatedView, presentationModel);
	}
}
