package org.robobinding.viewattribute.view;

import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.viewattribute.view.AbstractVisibilityAttribute.BooleanVisibilityAttribute;
import org.robobinding.viewattribute.view.AbstractVisibilityAttribute.IntegerVisibilityAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class AbstractVisibilityAttributeTest extends AbstractMultiTypePropertyViewAttributeTest<ViewVisibilityAttribute> {
    @Override
    protected void setTypeMappingExpectations() {
	forPropertyType(int.class).expectAttribute(IntegerVisibilityAttribute.class);
	forPropertyType(Integer.class).expectAttribute(IntegerVisibilityAttribute.class);
	forPropertyType(boolean.class).expectAttribute(BooleanVisibilityAttribute.class);
	forPropertyType(Boolean.class).expectAttribute(BooleanVisibilityAttribute.class);
    }

}
