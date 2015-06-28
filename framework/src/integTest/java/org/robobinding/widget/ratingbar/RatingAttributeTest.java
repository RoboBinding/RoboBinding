package org.robobinding.widget.ratingbar;

import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widget.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.widget.ratingbar.RatingAttribute.FloatRatingAttribute;
import org.robobinding.widget.ratingbar.RatingAttribute.IntegerRatingAttribute;
import org.robolectric.annotation.Config;

import android.widget.RatingBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class RatingAttributeTest extends AbstractMultiTypePropertyViewAttributeTest {
	@Override
	protected void setTypeMappingExpectations() {
		forPropertyType(int.class).expectAttributeType(IntegerRatingAttribute.class);
		forPropertyType(Integer.class).expectAttributeType(IntegerRatingAttribute.class);
		forPropertyType(float.class).expectAttributeType(FloatRatingAttribute.class);
		forPropertyType(Float.class).expectAttributeType(FloatRatingAttribute.class);
	}
	
	@Override
	protected TwoWayPropertyViewAttribute<RatingBar, ?, ?> createViewAttribute(Class<?> propertyType) {
		return new RatingAttribute().create(null, propertyType);
	}
}
