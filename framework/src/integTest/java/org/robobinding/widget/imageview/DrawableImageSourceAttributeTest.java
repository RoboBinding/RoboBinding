package org.robobinding.widget.imageview;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.BitmapDrawableData;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.imageview.ImageSourceAttribute.DrawableImageSourceAttribute;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.widget.ImageView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class DrawableImageSourceAttributeTest {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		ImageView view = new ImageView(Robolectric.application);
		DrawableImageSourceAttribute attribute = new DrawableImageSourceAttribute();
		BitmapDrawableData drawableData = RandomValues.anyBitmapDrawableData(Robolectric.application);

		attribute.updateView(view, drawableData.drawable);

		assertThat(Robolectric.shadowOf(view.getDrawable()).getCreatedFromResId(), equalTo(drawableData.resourceId));
	}
}
