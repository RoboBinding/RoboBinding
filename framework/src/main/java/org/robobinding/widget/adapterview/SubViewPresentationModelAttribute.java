package org.robobinding.widget.adapterview;

import org.robobinding.BindingContext;
import org.robobinding.SubBindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributeWithAttribute;

import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
class SubViewPresentationModelAttribute implements ChildViewAttributeWithAttribute<ValueModelAttribute> {
	private final ViewGroup parent;
	private final int layoutId;
	private final SubViewHolder subViewHolder;
	private ValueModelAttribute attribute;

	public SubViewPresentationModelAttribute(ViewGroup parent, int layoutId, SubViewHolder subViewHolder) {
		this.parent = parent;
		this.layoutId = layoutId;
		this.subViewHolder = subViewHolder;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		SubBindingContext subBindingContext = bindingContext.navigateToSubContext(attribute.getPropertyName());
		View subView = subBindingContext.inflateAndBindWithoutAttachingToRoot(layoutId, parent);
		subViewHolder.setSubView(subView);
	}

	@Override
	public void setAttribute(ValueModelAttribute attribute) {
		this.attribute = attribute;
	}
}