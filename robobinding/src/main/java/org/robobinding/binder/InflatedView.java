package org.robobinding.binder;

import java.util.List;

import org.robobinding.BindingContext;
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class InflatedView {
    private View rootView;
    List<ResolvedBindingAttributesForView> childViewBindingAttributesGroup;
    private ViewHierarchyInflationErrorsException errors;

    InflatedView(View rootView, List<ResolvedBindingAttributesForView> childViewBindingAttributesGroup,
    	ViewHierarchyInflationErrorsException errors) {
        this.rootView = rootView;
        this.childViewBindingAttributesGroup = childViewBindingAttributesGroup;
        this.errors = errors;
    }

    public View getRootView() {
        return rootView;
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