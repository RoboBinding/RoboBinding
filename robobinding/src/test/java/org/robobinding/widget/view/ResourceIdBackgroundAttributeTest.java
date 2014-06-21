package org.robobinding.widget.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.DrawableData;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.property.AbstractPropertyViewAttributeTest;
import org.robobinding.widget.view.BackgroundAttribute.ResourceIdBackgroundAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ResourceIdBackgroundAttributeTest extends AbstractPropertyViewAttributeTest<View, ResourceIdBackgroundAttribute> {
    @Test
    public void givenBoundAttribute_whenUpdateView_thenViewShouldReflectChanges() {
	DrawableData drawableData = RandomValues.anyDrawableData();

	attribute.updateView(view, drawableData.resourceId);

	assertThat(view.getBackground(), equalTo(drawableData.drawable));
    }
}