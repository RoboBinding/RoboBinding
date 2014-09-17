package org.robobinding.widget.adapterview;

import org.robobinding.BindingContext;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class StaticLayoutAttribute implements ChildViewAttribute {
	private final RowLayoutUpdater rowLayoutUpdater;
	private final StaticResourceAttribute attribute;

	public StaticLayoutAttribute(RowLayoutUpdater rowLayoutUpdater, StaticResourceAttribute attribute) {
		this.rowLayoutUpdater = rowLayoutUpdater;
		this.attribute = attribute;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		int itemLayoutId = attribute.getResourceId(bindingContext.getContext());
		rowLayoutUpdater.updateRowLayout(itemLayoutId);
	}
}