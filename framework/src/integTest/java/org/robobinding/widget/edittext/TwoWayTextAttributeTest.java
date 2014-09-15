package org.robobinding.widget.edittext;

import org.robobinding.widget.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.widget.edittext.TwoWayTextAttribute.TwoWayCharSequenceTextAttribute;
import org.robobinding.widget.edittext.TwoWayTextAttribute.TwoWayStringTextAttribute;
import org.robolectric.annotation.Config;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
public class TwoWayTextAttributeTest extends
		AbstractMultiTypePropertyViewAttributeTest<TwoWayTextAttribute> {
	@Override
	protected void setTypeMappingExpectations() {
		forPropertyType(CharSequence.class).expectAttribute(
				TwoWayCharSequenceTextAttribute.class);
		forPropertyType(String.class).expectAttribute(
				TwoWayStringTextAttribute.class);
	}
}
