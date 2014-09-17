package org.robobinding.widget.imageview;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.BitmapDrawableData;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.imageview.ImageSourceAttribute.BitmapImageSourceAttribute;
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
public class BitmapImageSourceAttributeTest {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		ImageView view = new ImageView(Robolectric.application);
		BitmapImageSourceAttribute attribute = new BitmapImageSourceAttribute();
		BitmapDrawableData drawableData = RandomValues.anyBitmapDrawableData(Robolectric.application);

		attribute.updateView(view, drawableData.bitmap);

		assertThat(Robolectric.shadowOf(view.getDrawable()).getCreatedFromResId(), equalTo(drawableData.resourceId));
	}
}
