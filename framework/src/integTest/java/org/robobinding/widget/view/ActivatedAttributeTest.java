package org.robobinding.widget.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
@Config(manifest=Config.NONE)
public class ActivatedAttributeTest extends
		AbstractPropertyViewAttributeTest<View, ActivatedAttribute> {
	@Ignore
	// View.activated property is not supported by Robolectric 1.2. The test
	// will be enabled once Robolectric is upgraded.
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		boolean activated = RandomValues.trueOrFalse();

		view.setActivated(!activated);

		attribute.updateView(view, activated);

		assertThat(view.isActivated(), equalTo(activated));
	}
}
