package org.robobinding.viewattribute.imageview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.DrawableData;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.imageview.ImageSourceAttribute.IntegerImageSourceAttribute;

import android.widget.ImageView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class IntegerImageSourceAttributeTest extends AbstractPropertyViewAttributeTest<ImageView, IntegerImageSourceAttribute> {
    @Test
    public void whenUpdatingValueModel_thenViewShouldReflectChanges() {
	DrawableData drawableData = RandomValues.anyDrawableData();

	attribute.valueModelUpdated(drawableData.resourceId);

	assertThat(view.getDrawable(), equalTo(drawableData.drawable));
    }
}
