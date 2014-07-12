package org.robobinding.widget.imageview;

import org.robobinding.util.PrimitiveTypeUtils;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ImageSourceAttribute implements MultiTypePropertyViewAttribute<ImageView> {
    @Override
    public PropertyViewAttribute<ImageView, ?> create(ImageView view, Class<?> propertyType) {
        if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType)) {
	    return new IntegerImageSourceAttribute();
	} else if (Drawable.class.isAssignableFrom(propertyType)) {
	    return new DrawableImageSourceAttribute();
	} else if (Bitmap.class.isAssignableFrom(propertyType)) {
	    return new BitmapImageSourceAttribute();
	}

	return null;
    }

    static class IntegerImageSourceAttribute implements PropertyViewAttribute<ImageView, Integer> {
	@Override
	public void updateView(ImageView view, Integer newResourceId) {
	    view.setImageResource(newResourceId);
	}
    }

    static class DrawableImageSourceAttribute implements PropertyViewAttribute<ImageView, Drawable> {
	@Override
	public void updateView(ImageView view, Drawable newDrawable) {
	    view.setImageDrawable(newDrawable);
	}
    }

    static class BitmapImageSourceAttribute implements PropertyViewAttribute<ImageView, Bitmap> {
	@Override
	public void updateView(ImageView view, Bitmap newBitmap) {
	    view.setImageBitmap(newBitmap);
	}
    }

}
