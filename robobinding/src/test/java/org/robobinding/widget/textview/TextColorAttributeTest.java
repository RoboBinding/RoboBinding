package org.robobinding.widget.textview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.property.AbstractPropertyViewAttributeTest;

import android.widget.TextView;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowTextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TextColorAttributeTest extends AbstractPropertyViewAttributeTest<TextView, TextColorAttribute> {
    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
	int newColor = RandomValues.anyColor();

	attribute.updateView(view, newColor);

	ShadowTextView shadowView = Robolectric.shadowOf(view);
	assertThat(shadowView.getTextColorHexValue(), equalTo(newColor));
    }
}
