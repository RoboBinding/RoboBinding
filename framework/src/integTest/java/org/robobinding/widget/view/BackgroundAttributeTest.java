package org.robobinding.widget.view;

import org.robobinding.widget.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.widget.view.BackgroundAttribute.BitmapBackgroundAttribute;
import org.robobinding.widget.view.BackgroundAttribute.DrawableBackgroundAttribute;
import org.robobinding.widget.view.BackgroundAttribute.ResourceIdBackgroundAttribute;
import org.robolectric.annotation.Config;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
public class BackgroundAttributeTest extends AbstractMultiTypePropertyViewAttributeTest<BackgroundAttribute> {
	@Override
	protected void setTypeMappingExpectations() {
		forPropertyType(int.class).expectAttribute(ResourceIdBackgroundAttribute.class);
		forPropertyType(Integer.class).expectAttribute(ResourceIdBackgroundAttribute.class);
		forPropertyType(Bitmap.class).expectAttribute(BitmapBackgroundAttribute.class);
		forPropertyType(Drawable.class).expectAttribute(DrawableBackgroundAttribute.class);
	}
}