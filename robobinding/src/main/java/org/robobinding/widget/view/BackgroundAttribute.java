package org.robobinding.widget.view;

import org.robobinding.util.PrimitiveTypeUtils;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BackgroundAttribute implements MultiTypePropertyViewAttribute<View> {
    public PropertyViewAttribute<View, ?> create(View view, Class<?> propertyType) {
	if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType)) {
	    return new ResourceIdBackgroundAttribute();
	} else if (Bitmap.class.isAssignableFrom(propertyType)) {
	    return new BitmapBackgroundAttribute();
	} else if (Drawable.class.isAssignableFrom(propertyType)) {
	    return new DrawableBackgroundAttribute();
	}

	return null;
    }

    static class ResourceIdBackgroundAttribute implements PropertyViewAttribute<View, Integer> {
	@Override
	public void updateView(View view, Integer newResourceId) {
	    view.setBackgroundResource(newResourceId);
	}
    }

    static class BitmapBackgroundAttribute implements PropertyViewAttribute<View, Bitmap> {
	@Override
	public void updateView(View view, Bitmap newBitmap) {
	    view.setBackgroundDrawable(new BitmapDrawable(newBitmap));
	}
    }

    static class DrawableBackgroundAttribute implements PropertyViewAttribute<View, Drawable> {
	@Override
	public void updateView(View view, Drawable newDrawable) {
	    view.setBackgroundDrawable(newDrawable);
	}
    }

}