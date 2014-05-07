package org.robobinding.viewattribute.imageview;

import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.PrimitiveTypeUtils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ImageSourceAttribute extends AbstractMultiTypePropertyViewAttribute<ImageView> {
    @Override
    protected AbstractPropertyViewAttribute<ImageView, ?> createPropertyViewAttribute(Class<?> propertyType) {
	if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType)) {
	    return new IntegerImageSourceAttribute();
	} else if (Drawable.class.isAssignableFrom(propertyType)) {
	    return new DrawableImageSourceAttribute();
	} else if (Bitmap.class.isAssignableFrom(propertyType)) {
	    return new BitmapImageSourceAttribute();
	}

	return null;
    }

    static class IntegerImageSourceAttribute extends AbstractReadOnlyPropertyViewAttribute<ImageView, Integer> {
	@Override
	protected void valueModelUpdated(Integer resourceId) {
	    view.setImageResource(resourceId);
	}
    }

    static class DrawableImageSourceAttribute extends AbstractReadOnlyPropertyViewAttribute<ImageView, Drawable> {
	@Override
	protected void valueModelUpdated(Drawable drawable) {
	    view.setImageDrawable(drawable);
	}
    }

    static class BitmapImageSourceAttribute extends AbstractReadOnlyPropertyViewAttribute<ImageView, Bitmap> {
	@Override
	protected void valueModelUpdated(Bitmap bitmap) {
	    view.setImageBitmap(bitmap);
	}
    }

}
