package org.robobinding.widget.compoundbutton;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.util.RandomValues;
import org.robobinding.viewattribute.ValueModelUtils;
import org.robolectric.annotation.Config;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class CheckedAttributeTest extends AbstractCompoundButtonAttributeTest {
	private CheckedAttribute attribute;
	
	@Before
	public void setUp() {
		attribute = new CheckedAttribute();
	}
	
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		boolean newState = RandomValues.trueOrFalse();

		attribute.updateView(view, newState, viewAddOn);

		assertThat(view.isChecked(), equalTo(newState));
	}

	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
		ValueModel<Boolean> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(viewAddOn, valueModel, view);

		boolean newValue = !view.isChecked();
		view.setChecked(newValue);

		assertThat(valueModel.getValue(), equalTo(newValue));
	}

	@Test
	public void whenObserveChangesOnTheView_thenRegisterWithViewListeners() {
		attribute.observeChangesOnTheView(viewAddOn, null, view);

		assertTrue(viewAddOn.addOnCheckedChangeListenerInvoked);
	}
}
