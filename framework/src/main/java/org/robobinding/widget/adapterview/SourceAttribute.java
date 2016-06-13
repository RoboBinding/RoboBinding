package org.robobinding.widget.adapterview;

import org.robobinding.BindingContext;
import org.robobinding.ItemBindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributeWithAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class SourceAttribute implements ChildViewAttributeWithAttribute<ValueModelAttribute> {
	private final RequiresItemBindingContext receiver;
	private ValueModelAttribute attribute;

	public SourceAttribute(final RequiresItemBindingContext receiver) {
		this.receiver = receiver;
	}

	@Override
	public void setAttribute(ValueModelAttribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		ItemBindingContext itemBindingContext = bindingContext.navigateToItemContext(attribute.getPropertyName());
		receiver.setBindingContext(itemBindingContext);
	}
	
	public static interface RequiresItemBindingContext {
		void setBindingContext(ItemBindingContext bindingContext);
	}
}