package org.robobinding.widget.adapterview;

import java.util.List;

import org.robobinding.BindingContext;
import org.robobinding.attribute.StaticResourcesAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttribute;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 */
class StaticLayoutsAttribute implements ChildViewAttribute {
	private final RowLayoutsUpdater rowLayoutsUpdater;
	private final StaticResourcesAttribute attribute;

	public StaticLayoutsAttribute(RowLayoutsUpdater rowLayoutsUpdater, StaticResourcesAttribute attribute) {
		this.rowLayoutsUpdater = rowLayoutsUpdater;
		this.attribute = attribute;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		List<Integer> itemLayoutIds = attribute.getResourceIds(bindingContext.getContext());
		rowLayoutsUpdater.updateRowLayouts(itemLayoutIds);
	}
}