package org.robobinding.widget.expandablelistview;

import org.robobinding.BindingContext;
import org.robobinding.attribute.AbstractPropertyAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributeWithAttribute;

/**
 *  @since 1.0
 *  @version $Revision: 1.0 $
 *  @author Jihun Lee
 */
public class ExpandableRowLayoutAttributeAdapter implements ChildViewAttributeWithAttribute<AbstractPropertyAttribute> {
    private ExpandableRowLayoutAttributeFactory layoutAttributeFactory;
    private ChildViewAttribute layoutAttribute;

    public ExpandableRowLayoutAttributeAdapter(ExpandableRowLayoutAttributeFactory layoutAttributeFactory) {
        this.layoutAttributeFactory = layoutAttributeFactory;
    }

    @Override
    public void setAttribute(AbstractPropertyAttribute attribute) {
        layoutAttribute = layoutAttributeFactory.createChildLayoutAttribute(attribute);
    }



    @Override
    public void bindTo(BindingContext bindingContext) {
        layoutAttribute.bindTo(bindingContext);
    }
}
