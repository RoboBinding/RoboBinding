package org.robobinding.widget.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;

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
    public void whenUpdateView_thenViewShouldReflectChanges() {
	int newColor = RandomValues.anyColor();

	attribute.updateView(view, newColor);

	ShadowView shadowView = Robolectric.shadowOf(view);
	assertThat(shadowView.getBackgroundColor(), equalTo(newColor));
    }

}
