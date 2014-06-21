package org.robobinding.widget.imageview;

import org.robobinding.viewattribute.property.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.widget.imageview.ImageSourceAttribute.BitmapImageSourceAttribute;
import org.robobinding.widget.imageview.ImageSourceAttribute.DrawableImageSourceAttribute;
import org.robobinding.widget.imageview.ImageSourceAttribute.IntegerImageSourceAttribute;

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
