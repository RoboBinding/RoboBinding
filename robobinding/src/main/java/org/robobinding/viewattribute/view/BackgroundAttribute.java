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
package org.robobinding.viewattribute.view;

import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.PrimitiveTypeUtils;

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
public class BackgroundAttribute extends AbstractMultiTypePropertyViewAttribute<View> {
    @Override
    protected AbstractPropertyViewAttribute<View, ?> createPropertyViewAttribute(Class<?> propertyType) {
	if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType)) {
	    return new ResourceIdBackgroundAttribute();
	} else if (Bitmap.class.isAssignableFrom(propertyType)) {
	    return new BitmapBackgroundAttribute();
	} else if (Drawable.class.isAssignableFrom(propertyType)) {
	    return new DrawableBackgroundAttribute();
	}

	throw new RuntimeException("Could not find a suitable background attribute class for property type: " + propertyType);
    }

    static class ResourceIdBackgroundAttribute extends AbstractReadOnlyPropertyViewAttribute<View, Integer> {
	@Override
	protected void valueModelUpdated(Integer newResourceId) {
	    view.setBackgroundResource(newResourceId);
	}
    }

    static class BitmapBackgroundAttribute extends AbstractReadOnlyPropertyViewAttribute<View, Bitmap> {
	@Override
	protected void valueModelUpdated(Bitmap newBitmap) {
	    view.setBackgroundDrawable(new BitmapDrawable(newBitmap));
	}
    }

    static class DrawableBackgroundAttribute extends AbstractReadOnlyPropertyViewAttribute<View, Drawable> {
	@Override
	protected void valueModelUpdated(Drawable newDrawable) {
	    view.setBackgroundDrawable(newDrawable);
	}
    }

}