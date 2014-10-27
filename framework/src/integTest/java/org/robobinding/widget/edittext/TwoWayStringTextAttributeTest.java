package org.robobinding.widget.edittext;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.robobinding.widget.textview.CharSequenceMatcher.sameAs;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.ValueModelUtils;
import org.robobinding.widget.edittext.TwoWayTextAttribute.TwoWayStringTextAttribute;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowTextView;

import android.widget.EditText;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TwoWayStringTextAttributeTest {
	private EditText view;
	private TwoWayStringTextAttribute attribute;

	@Before
	public void setUp() {
		view = new EditText(Robolectric.application);
		attribute = new TwoWayStringTextAttribute();
	}

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		String newText = RandomStringUtils.randomAlphanumeric(5);

		attribute.updateView(view, newText);

		assertThat(view.getText(), sameAs(newText));
	}

	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
		ValueModel<String> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(view, valueModel);

		view.setText(RandomStringUtils.random(5));

		assertThat(valueModel.getValue(), sameAs(view.getText()));
	}

	@Test
	public void givenALateValueCommitAttribute_whenUpdatingView_thenDoNotImmediatelyCommitToValueModel() {
		attribute.setValueCommitMode(ValueCommitMode.ON_FOCUS_LOST);
		String newText = RandomStringUtils.randomAlphanumeric(5);
		ValueModel<String> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(view, valueModel);

		view.setText(newText);

		assertThat(valueModel.getValue(), not(sameAs(newText)));
	}

	@Test
	public void givenALateValueCommitAttribute_whenViewLosesFocus_thenCommitToValueModel() {
		attribute.setValueCommitMode(ValueCommitMode.ON_FOCUS_LOST);
		String newText = RandomStringUtils.randomAlphanumeric(5);
		ValueModel<String> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(view, valueModel);

		view.setText(newText);

		ShadowTextView shadowTextView = Robolectric.shadowOf(view);
		shadowTextView.setViewFocus(false);

		assertThat(valueModel.getValue(), sameAs(newText));
	}
}
