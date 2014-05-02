package org.robobinding.viewattribute.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.DrawableData;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.view.BackgroundAttribute.BitmapBackgroundAttribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BitmapBackgroundAttributeTest extends AbstractPropertyViewAttributeTest<View, BitmapBackgroundAttribute> {
    @Test
    public void givenBoundAttribute_whenValueModelUpdated_thenViewShouldReflectChanges() {
	DrawableData drawableData = RandomValues.anyDrawableData();

	attribute.valueModelUpdated(drawableData.bitmap);

	assertThat(view.getBackground(), equalTo(drawableData.drawable));
    }
}