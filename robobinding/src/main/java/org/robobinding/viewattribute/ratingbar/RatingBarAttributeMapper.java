package org.robobinding.viewattribute.ratingbar;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

import android.widget.RatingBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingBarAttributeMapper implements BindingAttributeMapper<RatingBar> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<RatingBar> mappings) {
	mappings.mapPropertyAttribute(RatingAttribute.class, "rating");
	mappings.mapPropertyAttribute(NumStarsAttribute.class, "numStars");

	mappings.mapCommandAttribute(OnRatingBarChangeAttribute.class, "onRatingBarChange");
    }
}
