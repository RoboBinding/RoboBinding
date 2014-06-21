package org.robobinding.widget.compoundbutton;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.property.AbstractPropertyViewAttributeWithViewListenersAwareTest;

import android.widget.CheckBox;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class CheckedAttributeTest extends
	AbstractPropertyViewAttributeWithViewListenersAwareTest<CheckBox, CheckedAttribute, MockCompoundButtonListeners> {
    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
	boolean checked = RandomValues.trueOrFalse();

	attribute.updateView(view, checked);

	assertThat(view.isChecked(), equalTo(checked));
    }

    @Test
    public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
	ValueModel<Boolean> valueModel = ValueModelUtils.create();
	attribute.observeChangesOnTheView(view, valueModel);

	boolean newValue = !view.isChecked();
	view.setChecked(newValue);

	assertThat(valueModel.getValue(), equalTo(newValue));
    }

    @Test
    public void whenObserveChangesOnTheView_thenRegisterWithViewListeners() {
	attribute.observeChangesOnTheView(view, null);

	assertTrue(viewListeners.addOnCheckedChangeListenerInvoked);
    }
}
