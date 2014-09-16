package org.robobinding.widget.view;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.BitmapDrawableData;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.view.BackgroundAttribute.BitmapBackgroundAttribute;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class BitmapBackgroundAttributeTest {
	@Test
	public void givenBoundAttribute_whenUpdateView_thenViewShouldReflectChanges() {
		View view = new View(Robolectric.application);
		BitmapBackgroundAttribute attribute = new BitmapBackgroundAttribute();
		BitmapDrawableData drawableData = RandomValues.anyBitmapDrawableData(Robolectric.application);

		attribute.updateView(view, drawableData.bitmap);

		assertThat(Robolectric.shadowOf(view.getBackground()).getCreatedFromResId(), 
				equalTo(drawableData.resourceId));
	}
}