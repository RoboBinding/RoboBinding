package org.robobinding.viewattribute.edittext;

import static org.junit.Assert.assertThat;
import static org.robobinding.viewattribute.textview.CharSequenceMatcher.sameAs;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.edittext.TwoWayTextAttribute.TwoWayCharSequenceTextAttribute;

import android.widget.EditText;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayCharSequenceAttributeTest extends AbstractPropertyViewAttributeTest<EditText, TwoWayCharSequenceTextAttribute> {
    @Test
    public void givenValueModelIsStringType_whenValueModelUpdated_thenViewShouldReflectChanges() {
	CharSequence newText = RandomStringUtils.randomAlphanumeric(5);

	attribute.valueModelUpdated(newText);

	assertThat(view.getText(), sameAs(newText));
    }

    @Test
    public void givenValueModelIsStringType_whenViewStateIsChanged_thenUpdateValueModel() {
	ValueModel<CharSequence> valueModel = twoWayBindToProperty(CharSequence.class);

	view.setText(RandomStringUtils.random(5));

	assertThat(valueModel.getValue(), sameAs(view.getText()));
    }
}
