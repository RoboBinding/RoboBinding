package org.robobinding.viewattribute.grouped;

import org.robobinding.BindingContext;
import org.robobinding.viewattribute.ViewAttributeBinder;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class GroupedViewAttributeBinder<ViewType> implements ViewAttributeBinder {
	private final ViewType view;
	private final InitializedGroupedViewAttribute<ViewType> viewAttribute;
	private final ChildViewAttributesBuilderImpl<ViewType> childViewAttributesBuilder;

	private ChildViewAttributes childViewAttributes;

	public GroupedViewAttributeBinder(ViewType view, InitializedGroupedViewAttribute<ViewType> viewAttribute,
			ChildViewAttributesBuilderImpl<ViewType> childViewAttributesBuilder) {
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

	private ChildViewAttributes initializeChildViewAttributes(BindingContext bindingContext) {
		viewAttribute.setupChildViewAttributes(view, childViewAttributesBuilder, bindingContext);
		return childViewAttributesBuilder.build();
	}

	@Override
	public void preInitializeView(BindingContext bindingContext) {
		childViewAttributes.preInitializeView(bindingContext);
	}
}
