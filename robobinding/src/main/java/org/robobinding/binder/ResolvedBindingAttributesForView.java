package org.robobinding.binder;

import java.util.Collection;
import java.util.List;

import org.robobinding.BindingContext;
import org.robobinding.internal.guava.Lists;
import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.ViewAttributeBinder;
import org.robobinding.viewattribute.grouped.AttributeGroupBindingException;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ResolvedBindingAttributesForView {
    private View view;
    private final List<ViewAttributeBinder> viewAttributes;

    ResolvedBindingAttributesForView(View view, Collection<ViewAttributeBinder> viewAttributes) {
	this.view = view;
	this.viewAttributes = Lists.newArrayList(viewAttributes);
    }

    public ViewBindingErrors bindTo(BindingContext bindingContext) {
	ViewBindingErrors viewBindingError = new ViewBindingErrors(view);
	for (ViewAttributeBinder viewAttribute : viewAttributes) {
	    try {
		viewAttribute.bindTo(bindingContext);
	    } catch (AttributeBindingException e) {
		viewBindingError.addAttributeError(e);
	    } catch (AttributeGroupBindingException e) {
		viewBindingError.addAttributeGroupError(e);
	    }
	}

	return viewBindingError;
    }

    public void preinitializeView(BindingContext bindingContext) {
	for (ViewAttributeBinder viewAttribute : viewAttributes) {
	    viewAttribute.preInitializeView(bindingContext);
	}
    }
}