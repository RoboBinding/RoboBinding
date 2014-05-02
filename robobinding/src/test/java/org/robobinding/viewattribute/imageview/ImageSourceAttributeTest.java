package org.robobinding.viewattribute.imageview;

import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.viewattribute.imageview.ImageSourceAttribute.BitmapImageSourceAttribute;
import org.robobinding.viewattribute.imageview.ImageSourceAttribute.DrawableImageSourceAttribute;
import org.robobinding.viewattribute.imageview.ImageSourceAttribute.IntegerImageSourceAttribute;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ImageSourceAttributeTest extends AbstractMultiTypePropertyViewAttributeTest<ImageSourceAttribute> {
    @Override
    protected void setTypeMappingExpectations() {
	forPropertyType(int.class).expectAttribute(IntegerImageSourceAttribute.class);
	forPropertyType(int.class).expectAttribute(IntegerImageSourceAttribute.class);
	forPropertyType(Bitmap.class).expectAttribute(BitmapImageSourceAttribute.class);
	forPropertyType(Drawable.class).expectAttribute(DrawableImageSourceAttribute.class);
    }

}
