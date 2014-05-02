package org.robobinding.viewattribute.view;

import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.viewattribute.view.BackgroundAttribute.BitmapBackgroundAttribute;
import org.robobinding.viewattribute.view.BackgroundAttribute.DrawableBackgroundAttribute;
import org.robobinding.viewattribute.view.BackgroundAttribute.ResourceIdBackgroundAttribute;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BackgroundAttributeTest extends AbstractMultiTypePropertyViewAttributeTest<BackgroundAttribute> {
    @Override
    protected void setTypeMappingExpectations() {
	forPropertyType(int.class).expectAttribute(ResourceIdBackgroundAttribute.class);
	forPropertyType(Integer.class).expectAttribute(ResourceIdBackgroundAttribute.class);
	forPropertyType(Bitmap.class).expectAttribute(BitmapBackgroundAttribute.class);
	forPropertyType(Drawable.class).expectAttribute(DrawableBackgroundAttribute.class);
    }
}