package org.robobinding.widget.adapterview;

import org.robobinding.BindingContext;
import org.robobinding.SubViewBinder;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.property.ValueModel;
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
		SubViewBinder viewBinder = bindingContext.createSubViewBinder();
		Object presentationModel = getPresentationModel(bindingContext, attribute.getPropertyName());
		bind(viewBinder, presentationModel);
	}

	void bind(SubViewBinder viewBinder, Object presentationModel) {
		View subView = viewBinder.inflateAndBindWithoutAttachingToRoot(layoutId, presentationModel, parent);
		subViewHolder.setSubView(subView);
	}
	
	Object getPresentationModel(BindingContext bindingContext, String presentationModelPropertyName) {
		ValueModel<Object> valueModel = bindingContext.getReadOnlyPropertyValueModel(presentationModelPropertyName);
		return valueModel.getValue();
	}

	@Override
	public void setAttribute(ValueModelAttribute attribute) {
		this.attribute = attribute;
	}
}