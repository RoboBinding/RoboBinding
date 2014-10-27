package org.robobinding.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BitmapDrawableData {
	public final int resourceId;
	public final Bitmap bitmap;
	public final BitmapDrawable drawable;

	public BitmapDrawableData(int resourceId, Bitmap bitmap, BitmapDrawable drawable) {
		this.resourceId = resourceId;
		this.bitmap = bitmap;
		this.drawable = drawable;
	}
}
