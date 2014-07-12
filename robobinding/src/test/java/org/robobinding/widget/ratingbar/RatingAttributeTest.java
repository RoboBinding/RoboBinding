package org.robobinding.widget.ratingbar;

import org.junit.runner.RunWith;
import org.robobinding.viewattribute.property.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.widget.ratingbar.RatingAttribute.FloatRatingAttribute;
import org.robobinding.widget.ratingbar.RatingAttribute.IntegerRatingAttribute;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class RatingAttributeTest extends AbstractMultiTypePropertyViewAttributeTest<RatingAttribute> {
    @Override
    protected void setTypeMappingExpectations() {
	forPropertyType(int.class).expectAttribute(IntegerRatingAttribute.class);
	forPropertyType(Integer.class).expectAttribute(IntegerRatingAttribute.class);
	forPropertyType(float.class).expectAttribute(FloatRatingAttribute.class);
	forPropertyType(Float.class).expectAttribute(FloatRatingAttribute.class);
    }
}
