package org.robobinding.viewattribute.grouped;

import org.robobinding.BindingContext;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface InitializedGroupedViewAttribute<ViewType> {
	void setupChildViewAttributes(ViewType view, ChildViewAttributesBuilder<ViewType> childViewAttributesBuilder);

	void postBind(ViewType view, BindingContext bindingContext);
}
