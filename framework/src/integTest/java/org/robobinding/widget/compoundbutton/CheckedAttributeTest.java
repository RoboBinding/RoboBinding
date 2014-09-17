package org.robobinding.widget.compoundbutton;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.util.RandomValues;
import org.robolectric.annotation.Config;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class CheckedAttributeTest extends AbstractCompoundButtonAttributeTest {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		CheckedAttribute attribute = new CheckedAttribute();
		boolean checked = RandomValues.trueOrFalse();

		attribute.updateView(view, checked);

		assertThat(view.isChecked(), equalTo(checked));
	}

	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
		CheckedAttribute attribute = withListenersSet(new CheckedAttribute());
		ValueModel<Boolean> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(view, valueModel);

		boolean newValue = !view.isChecked();
		view.setChecked(newValue);

		assertThat(valueModel.getValue(), equalTo(newValue));
	}

	@Test
	public void whenObserveChangesOnTheView_thenRegisterWithViewListeners() {
		CheckedAttribute attribute = withListenersSet(new CheckedAttribute());
		attribute.observeChangesOnTheView(view, null);

		assertTrue(viewListeners.addOnCheckedChangeListenerInvoked);
	}
}
