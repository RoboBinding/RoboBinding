package org.robobinding.widget.edittext;

import static org.junit.Assert.assertThat;
import static org.robobinding.widget.textview.CharSequenceMatcher.sameAs;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.ValueModelUtils;
import org.robobinding.widget.edittext.TwoWayTextAttribute.TwoWayCharSequenceTextAttribute;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.widget.EditText;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TwoWayCharSequenceAttributeTest {
	private EditText view;
	private TwoWayCharSequenceTextAttribute attribute;

	@Before
	public void setUp() {
		view = new EditText(Robolectric.application);
		attribute = new TwoWayCharSequenceTextAttribute();
	}

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		CharSequence newText = RandomStringUtils.randomAlphanumeric(5);

		attribute.updateView(view, newText);

		assertThat(view.getText(), sameAs(newText));
	}

	@Test
	public void whenViewStateIsChanged_thenValueModelShouldReceiveTheChange() {
		ValueModel<CharSequence> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(view, valueModel);

		view.setText(RandomStringUtils.random(5));

		assertThat(valueModel.getValue(), sameAs(view.getText()));
	}
}
