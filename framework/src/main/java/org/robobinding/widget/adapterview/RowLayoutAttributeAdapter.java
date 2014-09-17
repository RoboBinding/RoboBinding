package org.robobinding.widget.adapterview;

import org.robobinding.BindingContext;
import org.robobinding.attribute.AbstractPropertyAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributeWithAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class RowLayoutAttributeAdapter implements ChildViewAttributeWithAttribute<AbstractPropertyAttribute> {
	private RowLayoutAttributeFactory layoutAttributeFactory;
	private ChildViewAttribute layoutAttribute;

	public RowLayoutAttributeAdapter(RowLayoutAttributeFactory layoutAttributeFactory) {
		this.layoutAttributeFactory = layoutAttributeFactory;
	}

	@Override
	public void setAttribute(AbstractPropertyAttribute attribute) {
		layoutAttribute = layoutAttributeFactory.createRowLayoutAttribute(attribute);
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		layoutAttribute.bindTo(bindingContext);
	}
}