package org.robobinding.viewattribute.textview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.RandomValues;

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
    public void whenValueModelUpdated_thenViewShouldReflectChanges() {
	int newColor = RandomValues.anyColor();

	attribute.valueModelUpdated(newColor);

	ShadowTextView shadowView = Robolectric.shadowOf(view);
	assertThat(shadowView.getTextColorHexValue(), equalTo(newColor));
    }
}
