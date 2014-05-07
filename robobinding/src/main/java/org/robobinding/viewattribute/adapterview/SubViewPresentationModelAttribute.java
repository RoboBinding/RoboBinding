package org.robobinding.viewattribute.adapterview;

import org.robobinding.BindingContext;
import org.robobinding.ViewBinder;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.ChildViewAttributeWithAttribute;

import android.view.View;
/**
*
* @since 1.0
* @version $Revision: 1.0 $
* @author Robert Taylor
* @author Cheng Wei
*/
class SubViewPresentationModelAttribute implements ChildViewAttributeWithAttribute<ValueModelAttribute> {
    private final int layoutId;
    private final SubViewHolder subViewHolder;
    private ValueModelAttribute attribute;

    public SubViewPresentationModelAttribute(int layoutId, SubViewHolder subViewHolder) {
	this.layoutId = layoutId;
	this.subViewHolder = subViewHolder;
    }

    @Override
    public void bindTo(BindingContext bindingContext) {
        ViewBinder viewBinder = bindingContext.createViewBinder();
        View subView = viewBinder.inflateAndBind(layoutId, attribute.getPropertyName());
        subViewHolder.setSubView(subView);
    }

    @Override
    public void setAttribute(ValueModelAttribute attribute) {
        this.attribute = attribute;
    }
}