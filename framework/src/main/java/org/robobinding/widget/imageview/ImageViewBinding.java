package org.robobinding.widget.imageview;

import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.ViewBinding;

import android.widget.ImageView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ImageViewBinding implements ViewBinding<ImageView> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<ImageView> mappings) {
		mappings.mapMultiTypeProperty(ImageSourceAttribute.class, "src");
	}

}
