package org.robobinding.widget.edittext;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.robobinding.widget.edittext.CharSequenceMatcher.sameAs;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.ValueModelUtils;
import org.robobinding.widget.edittext.TwoWayTextAttribute.TwoWayStringTextAttribute;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
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
		view = new EditText(RuntimeEnvironment.application);
		attribute = new TwoWayStringTextAttribute();
	}

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		String newText = RandomStringUtils.randomAlphanumeric(5);

		attribute.updateView(view, newText, null);

		assertThat(view.getText(), sameAs(newText));
	}

	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
		ValueModel<String> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(null, valueModel, view);

		view.setText(RandomStringUtils.random(5));

		assertThat(valueModel.getValue(), sameAs(view.getText()));
	}

	@Test
	public void givenALateValueCommitAttribute_whenUpdatingView_thenDoNotImmediatelyCommitToValueModel() {
		attribute.setValueCommitMode(ValueCommitMode.ON_FOCUS_LOST);
		String newText = RandomStringUtils.randomAlphanumeric(5);
		ValueModel<String> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(null, valueModel, view);

		view.setText(newText);

		assertThat(valueModel.getValue(), not(sameAs(newText)));
	}

	@Test
	public void givenALateValueCommitAttribute_whenViewLosesFocus_thenCommitToValueModel() {
		attribute.setValueCommitMode(ValueCommitMode.ON_FOCUS_LOST);
		String newText = RandomStringUtils.randomAlphanumeric(5);
		ValueModel<String> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(null, valueModel, view);

		view.setText(newText);

		ShadowTextView shadowTextView = Shadows.shadowOf(view);
		shadowTextView.setViewFocus(false);

		assertThat(valueModel.getValue(), sameAs(newText));
	}
}
