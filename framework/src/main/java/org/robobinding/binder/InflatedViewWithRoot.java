package org.robobinding.binder;

import java.util.List;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class InflatedViewWithRoot extends InflatedView {
	private final View rootView;

	InflatedViewWithRoot(View rootView, List<ResolvedBindingAttributesForView> childViewBindingAttributesGroup, ViewHierarchyInflationErrorsException errors) {
		super(childViewBindingAttributesGroup, errors);
		this.rootView = rootView;
	}

	public View getRootView() {
		return rootView;
	}
}