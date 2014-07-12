package org.robobinding.viewattribute.grouped;

import org.robobinding.BindingContext;
import org.robobinding.viewattribute.ViewAttributeBinder;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class GroupedViewAttributeBinder<T extends View> implements ViewAttributeBinder {
    private final T view;
    private final InitializedGroupedViewAttribute<T> viewAttribute;
    private final ChildViewAttributesBuilderImpl<T> childViewAttributesBuilder;

    private ChildViewAttributes childViewAttributes;

    public GroupedViewAttributeBinder(T view, InitializedGroupedViewAttribute<T> viewAttribute,
	    ChildViewAttributesBuilderImpl<T> childViewAttributesBuilder) {
	this.view = view;
	this.viewAttribute = viewAttribute;
	this.childViewAttributesBuilder = childViewAttributesBuilder;
    }

    @Override
    public void bindTo(BindingContext bindingContext) {
	childViewAttributes = initializeChildViewAttributes(bindingContext);
	childViewAttributes.bindTo(bindingContext);
	viewAttribute.postBind(view, bindingContext);
    }

    private ChildViewAttributes initializeChildViewAttributes(BindingContext bindingContext)
    {
	viewAttribute.setupChildViewAttributes(view, childViewAttributesBuilder, bindingContext);
	return childViewAttributesBuilder.build();
    }

    @Override
    public void preInitializeView(BindingContext bindingContext) {
	childViewAttributes.preInitializeView(bindingContext);
    }
}
