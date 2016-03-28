package org.robobinding.widget.adapterview;

import org.robobinding.BindingContext;
import org.robobinding.viewattribute.grouped.ChildViewAttribute;

import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
class SubViewWithoutPresentationModelAttribute implements ChildViewAttribute {
	private final ViewGroup parent;
	private final int layoutId;
	private final SubViewHolder subViewHolder;

	public SubViewWithoutPresentationModelAttribute(ViewGroup parent, int layoutId, SubViewHolder subViewHolder) {
		this.parent = parent;
		this.layoutId = layoutId;
		this.subViewHolder = subViewHolder;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		View subView = bindingContext.inflateWithoutAttachingToRoot(layoutId, parent);
		subViewHolder.setSubView(subView);
	}
}