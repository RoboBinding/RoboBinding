package org.robobinding.viewattribute.imageview;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

import android.widget.ImageView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ImageViewAttributeMapper implements BindingAttributeMapper<ImageView> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<ImageView> mappings) {
	mappings.mapPropertyAttribute(ImageSourceAttribute.class, "src");
    }

}
