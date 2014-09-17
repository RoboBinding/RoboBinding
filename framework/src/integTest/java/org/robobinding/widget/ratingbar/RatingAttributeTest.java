package org.robobinding.widget.ratingbar;

import org.robobinding.widget.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.widget.ratingbar.RatingAttribute.FloatRatingAttribute;
import org.robobinding.widget.ratingbar.RatingAttribute.IntegerRatingAttribute;
import org.robolectric.annotation.Config;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class RatingAttributeTest extends AbstractMultiTypePropertyViewAttributeTest<RatingAttribute> {
	@Override
	protected void setTypeMappingExpectations() {
		forPropertyType(int.class).expectAttribute(IntegerRatingAttribute.class);
		forPropertyType(Integer.class).expectAttribute(IntegerRatingAttribute.class);
		forPropertyType(float.class).expectAttribute(FloatRatingAttribute.class);
		forPropertyType(Float.class).expectAttribute(FloatRatingAttribute.class);
	}
}
