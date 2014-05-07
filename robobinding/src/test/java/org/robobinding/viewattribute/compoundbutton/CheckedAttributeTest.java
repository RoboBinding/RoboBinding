package org.robobinding.viewattribute.compoundbutton;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.view.AbstractPropertyViewAttributeWithViewListenersAwareTest;

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
    public void whenValueModelUpdated_thenViewShouldReflectChanges() {
	boolean checked = RandomValues.trueOrFalse();

	attribute.valueModelUpdated(checked);

	assertThat(view.isChecked(), equalTo(checked));
    }

    @Test
    public void whenViewIsChecked_thenUpdateValueModel() {
	ValueModel<Boolean> valueModel = twoWayBindToProperty(Boolean.class);

	boolean newValue = !view.isChecked();
	view.setChecked(newValue);

	assertThat(valueModel.getValue(), equalTo(newValue));
    }

    @Test
    public void whenTwoWayBinding_thenRegisterWithViewListeners() {
	twoWayBindToProperty(Boolean.class);

	assertTrue(viewListeners.addOnCheckedChangeListenerInvoked);
    }
}
