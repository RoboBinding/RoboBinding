package org.robobinding.viewattribute.edittext;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.robobinding.viewattribute.textview.CharSequenceMatcher.sameAs;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.edittext.TwoWayTextAttribute.TwoWayStringTextAttribute;

import android.widget.EditText;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowTextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayStringTextAttributeTest extends AbstractPropertyViewAttributeTest<EditText, TwoWayStringTextAttribute> {
    @Test
    public void givenValueModelIsStringType_whenValueModelUpdated_thenViewShouldReflectChanges() {
	String newText = RandomStringUtils.randomAlphanumeric(5);

	attribute.valueModelUpdated(newText);

	assertThat(view.getText(), sameAs(newText));
    }

    @Test
    public void givenValueModelIsStringType_whenViewStateIsChanged_thenUpdateValueModel() {
	ValueModel<String> valueModel = twoWayBindToProperty(String.class);

	view.setText(RandomStringUtils.random(5));

	assertThat(valueModel.getValue(), sameAs(view.getText()));
    }

    @Test
    public void givenALateValueCommitAttribute_whenUpdatingView_thenDoNotImmediatelyCommitToValueModel() {
	attribute.setValueCommitMode(ValueCommitMode.ON_FOCUS_LOST);
	String newText = RandomStringUtils.randomAlphanumeric(5);
	ValueModel<String> valueModel = twoWayBindToProperty(String.class);

	view.setText(newText);

	assertThat(valueModel.getValue(), not(sameAs(newText)));
    }

    @Test
    public void givenALateValueCommitAttribute_whenViewLosesFocus_thenCommitToValueModel() {
	attribute.setValueCommitMode(ValueCommitMode.ON_FOCUS_LOST);
	String newText = RandomStringUtils.randomAlphanumeric(5);
	ValueModel<String> valueModel = twoWayBindToProperty(String.class);

	view.setText(newText);

	ShadowTextView shadowTextView = Robolectric.shadowOf(view);
	shadowTextView.setViewFocus(false);

	assertThat(valueModel.getValue(), sameAs(newText));
    }
}
