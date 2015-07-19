package org.robobinding.widget.edittext;

import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widget.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.widget.edittext.TwoWayTextAttribute.TwoWayCharSequenceTextAttribute;
import org.robobinding.widget.edittext.TwoWayTextAttribute.TwoWayStringTextAttribute;
import org.robolectric.annotation.Config;

import android.widget.EditText;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
public class TwoWayTextAttributeTest extends AbstractMultiTypePropertyViewAttributeTest {
	@Override
	protected void setTypeMappingExpectations() {
		forPropertyType(CharSequence.class).expectAttributeType(TwoWayCharSequenceTextAttribute.class);
		forPropertyType(String.class).expectAttributeType(TwoWayStringTextAttribute.class);
	}
	
	@Override
	protected TwoWayPropertyViewAttribute<EditText, ?, ?> createViewAttribute(Class<?> propertyType) {
		return new TwoWayTextAttribute().create(null, propertyType);
	}
}
