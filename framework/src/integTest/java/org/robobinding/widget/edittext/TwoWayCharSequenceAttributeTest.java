package org.robobinding.widget.edittext;

import static org.junit.Assert.assertThat;
import static org.robobinding.widget.edittext.CharSequenceMatcher.sameAs;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.ValueModelUtils;
import org.robobinding.widget.edittext.TwoWayTextAttribute.TwoWayCharSequenceTextAttribute;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
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
		view = new EditText(RuntimeEnvironment.application);
		attribute = new TwoWayCharSequenceTextAttribute();
	}

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		CharSequence newText = RandomStringUtils.randomAlphanumeric(5);

		attribute.updateView(view, newText, null);

		assertThat(view.getText(), sameAs(newText));
	}

	@Test
	public void whenViewStateIsChanged_thenValueModelShouldReceiveTheChange() {
		ValueModel<CharSequence> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(null, valueModel, view);

		view.setText(RandomStringUtils.random(5));

		assertThat(valueModel.getValue(), sameAs(view.getText()));
	}
}
