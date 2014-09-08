package org.robobinding.viewattribute;

import android.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DrawableData {
    private static DrawableData[] drawableData = {new DrawableData(R.drawable.bottom_bar), new DrawableData(R.drawable.title_bar)};

    public final int resourceId;
    public final Bitmap bitmap;
    public final Drawable drawable;

    public DrawableData(int resourceId) {
	this.resourceId = resourceId;
	this.bitmap = BitmapFactory.decodeResource(new Activity().getResources(), this.resourceId);
	this.drawable = new BitmapDrawable(bitmap);
    }

    public static DrawableData get(int index) {
	return drawableData[index];
    }

    public static int numDrawableData() {
	return drawableData.length;
    }
}
