package org.robobinding.widget.imageview;

import org.robobinding.util.PrimitiveTypeUtils;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ImageSourceAttribute implements OneWayMultiTypePropertyViewAttribute<ImageView> {
	@Override
	public OneWayPropertyViewAttribute<ImageView, ?> create(ImageView view, Class<?> propertyType) {
		if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType)) {
			return new IntegerImageSourceAttribute();
		} else if (Drawable.class.isAssignableFrom(propertyType)) {
			return new DrawableImageSourceAttribute();
		} else if (Bitmap.class.isAssignableFrom(propertyType)) {
			return new BitmapImageSourceAttribute();
		}

		return null;
	}

	static class IntegerImageSourceAttribute implements OneWayPropertyViewAttribute<ImageView, Integer> {
		@Override
		public void updateView(ImageView view, Integer newResourceId) {
			view.setImageResource(newResourceId);
		}
	}

	static class DrawableImageSourceAttribute implements OneWayPropertyViewAttribute<ImageView, Drawable> {
		@Override
		public void updateView(ImageView view, Drawable newDrawable) {
			view.setImageDrawable(newDrawable);
		}
	}

	static class BitmapImageSourceAttribute implements OneWayPropertyViewAttribute<ImageView, Bitmap> {
		@Override
		public void updateView(ImageView view, Bitmap newBitmap) {
			view.setImageBitmap(newBitmap);
		}
	}

}
