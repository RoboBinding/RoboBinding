package org.robobinding.widget.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.DrawableData;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.property.AbstractPropertyViewAttributeTest;
import org.robobinding.widget.view.BackgroundAttribute.BitmapBackgroundAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BitmapBackgroundAttributeTest extends AbstractPropertyViewAttributeTest<View, BitmapBackgroundAttribute> {
    @Test
    public void givenBoundAttribute_whenUpdateView_thenViewShouldReflectChanges() {
	DrawableData drawableData = RandomValues.anyDrawableData();

	attribute.updateView(view, drawableData.bitmap);

	assertThat(view.getBackground(), equalTo(drawableData.drawable));
    }
}