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
public class GroupedViewAttributeBinder implements ViewAttributeBinder {
	private final Object view;
	private final InitializedGroupedViewAttribute<Object> viewAttribute;
	private final ChildViewAttributesBuilderImpl<Object> childViewAttributesBuilder;

	private ChildViewAttributes childViewAttributes;

	public GroupedViewAttributeBinder(Object view, InitializedGroupedViewAttribute<Object> viewAttribute,
			ChildViewAttributesBuilderImpl<Object> childViewAttributesBuilder) {
		this.view = view;
		this.viewAttribute = viewAttribute;
		this.childViewAttributesBuilder = childViewAttributesBuilder;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		childViewAttributes = initializeChildViewAttributes();
		childViewAttributes.bindTo(bindingContext);
		viewAttribute.postBind(view, bindingContext);
	}

	private ChildViewAttributes initializeChildViewAttributes() {
		viewAttribute.setupChildViewAttributes(view, childViewAttributesBuilder);
		return childViewAttributesBuilder.build();
	}

	@Override
	public void preInitializeView(BindingContext bindingContext) {
		childViewAttributes.preInitializeView(bindingContext);
	}
}
