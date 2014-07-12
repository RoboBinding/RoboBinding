package org.robobinding.widget.edittext;

import org.robobinding.viewattribute.property.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.widget.edittext.TwoWayTextAttribute.TwoWayCharSequenceTextAttribute;
import org.robobinding.widget.edittext.TwoWayTextAttribute.TwoWayStringTextAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TwoWayTextAttributeTest extends AbstractMultiTypePropertyViewAttributeTest<TwoWayTextAttribute> {
    @Override
    protected void setTypeMappingExpectations() {
	forPropertyType(CharSequence.class).expectAttribute(TwoWayCharSequenceTextAttribute.class);
	forPropertyType(String.class).expectAttribute(TwoWayStringTextAttribute.class);
    }
}
