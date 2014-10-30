package org.robobinding.widget.radiogroup;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.widgetaddon.ViewAddOnAware;
import org.robobinding.widgetaddon.radiogroup.RadioGroupListeners;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.widget.RadioGroup;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractRadioGroupAttributeTest {
	protected RadioGroup view;
	protected MockRadioGroupListeners viewListeners;

	@Before
	public void initializeViewAndListeners() {
		view = new RadioGroup(Robolectric.application);
		viewListeners = new MockRadioGroupListeners(view);
	}

	public <T extends ViewAddOnAware<RadioGroupListeners>> T withListenersSet(T attribute) {
		attribute.setViewAddOn(viewListeners);
		return attribute;
	}
}
