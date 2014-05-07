package org.robobinding.viewattribute.ratingbar;

import org.junit.runner.RunWith;
import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.viewattribute.ratingbar.RatingAttribute.FloatRatingAttribute;
import org.robobinding.viewattribute.ratingbar.RatingAttribute.IntegerRatingAttribute;

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
	forPropertyType(int.class).expectAttribute(IntegerRatingAttribute.class);
	forPropertyType(float.class).expectAttribute(FloatRatingAttribute.class);
	forPropertyType(Float.class).expectAttribute(FloatRatingAttribute.class);
    }
}
