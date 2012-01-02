/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
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
public class Drawables
{
	private static DrawableData[] drawableData = {new DrawableData(R.drawable.bottom_bar), new DrawableData(R.drawable.title_bar)};
	
	public static DrawableData get(int index)
	{
		return drawableData[index];
	}
	
	public static class DrawableData
	{
		public final int resourceId;
		public final Bitmap bitmap;
		public final Drawable drawable;
		
		public DrawableData(int resourceId)
		{
			this.resourceId = resourceId;
			this.bitmap = BitmapFactory.decodeResource(new Activity().getResources(), this.resourceId);
			this.drawable = new BitmapDrawable(bitmap);
		}
	}
}
