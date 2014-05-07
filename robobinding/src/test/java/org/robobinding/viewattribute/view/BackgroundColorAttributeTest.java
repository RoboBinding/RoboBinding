package org.robobinding.viewattribute.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.RandomValues;

import android.view.View;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BackgroundColorAttributeTest extends AbstractPropertyViewAttributeTest<View, BackgroundColorAttribute> {
    @Test
    public void whenValueModelUpdated_thenViewShouldReflectChanges() {
	int newColor = RandomValues.anyColor();

	attribute.valueModelUpdated(newColor);

	ShadowView shadowView = Robolectric.shadowOf(view);
	assertThat(shadowView.getBackgroundColor(), equalTo(newColor));
    }

}
