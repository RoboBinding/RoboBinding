package org.robobinding.viewattribute.adapterview;

import org.robobinding.BindingContext;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.viewattribute.ViewAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class StaticLayoutAttribute implements ViewAttribute {
    private final StaticResourceAttribute attributeValue;
    private final RowLayoutUpdater rowLayoutUpdater;

    public StaticLayoutAttribute(StaticResourceAttribute attributeValue, RowLayoutUpdater rowLayoutUpdater) {
	this.attributeValue = attributeValue;
	this.rowLayoutUpdater = rowLayoutUpdater;
    }

    @Override
    public void bindTo(BindingContext bindingContext) {
	int itemLayoutId = attributeValue.getResourceId(bindingContext.getContext());
	rowLayoutUpdater.updateRowLayout(itemLayoutId);
    }

    @Override
    public void preInitializeView(BindingContext bindingContext) {
    }
}