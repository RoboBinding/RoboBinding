package org.robobinding.widget.adapterview;

import org.robobinding.BindingContext;
import org.robobinding.ViewBinder;
import org.robobinding.viewattribute.grouped.ChildViewAttribute;

import android.view.View;

/**
*
* @since 1.0
* @version $Revision: 1.0 $
* @author Robert Taylor
* @author Cheng Wei
*/
class SubViewWithoutPresentationModelAttribute implements ChildViewAttribute {
    private final int layoutId;
    private final SubViewHolder subViewHolder;

    public SubViewWithoutPresentationModelAttribute(int layoutId, SubViewHolder subViewHolder) {
	this.layoutId = layoutId;
	this.subViewHolder = subViewHolder;
    }

    @Override
    public void bindTo(BindingContext bindingContext) {
        ViewBinder viewBinder = bindingContext.createViewBinder();
        View subView = viewBinder.inflate(layoutId);
        subViewHolder.setSubView(subView);
    }
}