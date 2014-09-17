package org.robobinding.widget.adapterview;

import org.robobinding.BindingContext;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributeWithAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
class SubViewLayoutAttribute implements ChildViewAttributeWithAttribute<StaticResourceAttribute> {
	private int layoutId;
	private StaticResourceAttribute attribute;

	@Override
	public void bindTo(BindingContext bindingContext) {
		layoutId = attribute.getResourceId(bindingContext.getContext());
	}

	@Override
	public void setAttribute(StaticResourceAttribute attribute) {
		this.attribute = attribute;
	}

	public int getLayoutId() {
		return layoutId;
	}
}