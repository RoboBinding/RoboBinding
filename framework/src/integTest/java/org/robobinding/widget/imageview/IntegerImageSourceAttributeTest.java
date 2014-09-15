package org.robobinding.widget.imageview;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.util.BitmapDrawableData;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
import org.robobinding.widget.imageview.ImageSourceAttribute.IntegerImageSourceAttribute;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import android.widget.ImageView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest=Config.NONE)
public class IntegerImageSourceAttributeTest extends
		AbstractPropertyViewAttributeTest<ImageView, IntegerImageSourceAttribute> {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		BitmapDrawableData drawableData = RandomValues.anyBitmapDrawableData(Robolectric.application);

		attribute.updateView(view, drawableData.resourceId);

		assertThat(Robolectric.shadowOf(view.getDrawable()).getCreatedFromResId(), equalTo(drawableData.resourceId));
	}
}
